package kr.co.solbipos.security.lucy;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeFilterRule;
import com.navercorp.lucy.security.xss.servletfilter.defender.Defender;

public class CustomXssEscapeFilterConfig {
    @SuppressWarnings("unused")
    private static final String DEFAULT_FILTER_RULE_FILENAME = "lucy-xss-servlet-filter-rule.xml";

    private static final Log LOG = LogFactory.getLog(CustomXssEscapeFilterConfig.class);

    private Map<String, Map<String, XssEscapeFilterRule>> urlRuleSetMap =
            new HashMap<String, Map<String, XssEscapeFilterRule>>();
    private Map<String, XssEscapeFilterRule> globalParamRuleMap =
            new HashMap<String, XssEscapeFilterRule>();
    private Map<String, Defender> defenderMap = new HashMap<String, Defender>();
    private Defender defaultDefender = null;
    private InputStream ruleResourceStream;

    private void addDefaultInfo(Element rootElement) {
        NodeList nodeList = rootElement.getElementsByTagName("default");

        if (nodeList.getLength() > 0) {
            Element element = (Element) nodeList.item(0);
            addDefaultInfoItems(element);
        }
    }

    public CustomXssEscapeFilterConfig(InputStream ruleResourceStream) {
        this.ruleResourceStream = ruleResourceStream;
        this.init();
    }

    /**
     * @param element Element
     * @return void
     */
    private void addDefaultInfoItems(Element element) {
        NodeList nodeList = element.getElementsByTagName("defender");

        if (nodeList.getLength() > 0) {
            defaultDefender = defenderMap.get(nodeList.item(0).getTextContent());

            if (defaultDefender == null)
                LOG.error("Error config 'Default defender': Not found '"
                        + nodeList.item(0).getTextContent() + "'");
        }
    }

    /**
     * @param rootElement Element
     * @return void
     */
    private void addGlobalParams(Element rootElement) {
        NodeList nodeList = rootElement.getElementsByTagName("global");

        if (nodeList.getLength() > 0) {
            Element params = (Element) nodeList.item(0);
            NodeList paramNodeList = params.getElementsByTagName("params");

            if (paramNodeList.getLength() > 0)
                globalParamRuleMap = createRequestParamRuleMap((Element) nodeList.item(0));
        }
    }

    /**
     * @param rootElement Element
     * @return void
     */
    private void addUrlRuleSet(Element rootElement) {
        NodeList nodeList = rootElement.getElementsByTagName("url-rule");

        for (int i = 0; nodeList.getLength() > 0 && i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            addUrlRule(element);
        }
    }

    /**
     * @param element Element
     * @return void
     */
    private void addUrlRule(Element element) {
        Map<String, XssEscapeFilterRule> paramRuleMap = null;
        String url = null;
        NodeList nodeList = element.getElementsByTagName("url");

        if (nodeList.getLength() > 0) {
            url = nodeList.item(0).getTextContent();

            if (addUrlDisableRule(url, nodeList))
                return;
        }

        nodeList = element.getElementsByTagName("params");

        if (nodeList.getLength() > 0)
            paramRuleMap = createRequestParamRuleMap((Element) nodeList.item(0));

        urlRuleSetMap.put(url, paramRuleMap);
    }

    /**
     * @param url String
     * @param nodeList NodeList
     * @return boolean
     */
    private boolean addUrlDisableRule(String url, NodeList nodeList) {
        Map<String, XssEscapeFilterRule> paramRuleMap = null;
        boolean result = false;

        if (!url.isEmpty()) {
            boolean disable = StringUtils.equalsIgnoreCase(
                    ((Element) nodeList.item(0)).getAttribute("disable"), "true") ? true : false;
            paramRuleMap = createRequestParamRuleMap(url, disable);

            if (paramRuleMap != null) {
                urlRuleSetMap.put(url, paramRuleMap);
                result = true;
            }
        }

        return result;
    }

    /**
     * @param element Element
     * @return Map&lt;String, XssEscapeFilterRule&gt;
     */
    private Map<String, XssEscapeFilterRule> createRequestParamRuleMap(Element element) {
        Map<String, XssEscapeFilterRule> urlRuleMap = new HashMap<String, XssEscapeFilterRule>();

        NodeList nodeList = element.getElementsByTagName("param");
        for (int i = 0; nodeList.getLength() > 0 && i < nodeList.getLength(); i++) {
            Element eachElement = (Element) nodeList.item(i);
            String name = eachElement.getAttribute("name");
            boolean useDefender =
                    StringUtils.equalsIgnoreCase(eachElement.getAttribute("useDefender"), "false")
                            ? false : true;
            boolean usePrefix =
                    StringUtils.equalsIgnoreCase(eachElement.getAttribute("usePrefix"), "true")
                            ? true : false;
            Defender defender = null;
            NodeList defenderNodeList = eachElement.getElementsByTagName("defender");

            if (defenderNodeList.getLength() > 0) {
                defender = defenderMap.get(defenderNodeList.item(0).getTextContent());

                if (defender == null)
                    LOG.error("Error config 'param defender': Not found '"
                            + nodeList.item(0).getTextContent() + "'");

            } else
                defender = defaultDefender;

            XssEscapeFilterRule urlRule = new XssEscapeFilterRule();

            urlRule.setName(name);
            urlRule.setUseDefender(useDefender);
            urlRule.setDefender(defender);
            urlRule.setUsePrefix(usePrefix);

            urlRuleMap.put(name, urlRule);
        }

        return urlRuleMap;
    }

    /**
     * @param url String
     * @param disable boolean
     * @return Map&lt;String, XssEscapeFilterRule&gt;
     */
    private Map<String, XssEscapeFilterRule> createRequestParamRuleMap(String url,
            boolean disable) {
        if (!disable) {
            return null;
        }

        Map<String, XssEscapeFilterRule> urlRuleMap = new HashMap<String, XssEscapeFilterRule>();
        XssEscapeFilterRule urlRule = new XssEscapeFilterRule();
        urlRule.setName(url);
        urlRule.setUseDefender(false);
        urlRuleMap.put(url, urlRule);

        return urlRuleMap;
    }

    /**
     * @param rootElement Element
     * @return void
     */
    private void addDefenders(Element rootElement) {
        NodeList nodeList = rootElement.getElementsByTagName("defenders");

        if (nodeList.getLength() > 0) {
            Element element = (Element) nodeList.item(0);
            addDefender(element);
        }
    }

    /**
     * @param element Element
     * @return void
     */
    private void addDefender(Element element) {
        NodeList nodeList = element.getElementsByTagName("defender");
        for (int i = 0; nodeList.getLength() > 0 && i < nodeList.getLength(); i++) {
            Element eachElement = (Element) nodeList.item(i);
            String name = getTagContent(eachElement, "name");
            String clazz = getTagContent(eachElement, "class");
            String[] args = getInitParams(eachElement);
            addDefender(name, clazz, args);
        }
    }

    /**
     * @param name String
     * @param clazz String
     * @param args String[]
     * @return void
     */
    private void addDefender(String name, String clazz, String[] args) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(clazz)) {
            String message = String.format(
                    "The defender's name('%s') or clazz('%s') is empty. This defender is ignored",
                    name, clazz);

            LOG.warn(message);
            return;
        }

        try {
            Defender defender = (Defender) Class.forName(clazz.trim()).newInstance();

            defender.init(args);
            defenderMap.put(name, defender);
        } catch (InstantiationException e) {
            rethrow(name, clazz, e);
        } catch (IllegalAccessException e) {
            rethrow(name, clazz, e);
        } catch (ClassNotFoundException e) {
            rethrow(name, clazz, e);
        }
    }

    /**
     * @param name String
     * @param clazz String
     * @param e Exception
     * @return void
     */
    private void rethrow(String name, String clazz, Exception e) {
        String message = String.format("Fail to add defender: name=%s, class=%s", name, clazz);

        throw new IllegalStateException(message, e);
    }

    /**
     * @param eachElement Element
     * @return String[]
     */
    private String[] getInitParams(Element eachElement) {
        NodeList initParamNodeList = eachElement.getElementsByTagName("init-param");

        if (initParamNodeList.getLength() == 0)
            return new String[] {};

        Element paramValueElement = (Element) initParamNodeList.item(0);
        NodeList paramValueNodeList = paramValueElement.getElementsByTagName("param-value");

        String[] args = new String[paramValueNodeList.getLength()];
        for (int j = 0; paramValueNodeList.getLength() > 0
                && j < paramValueNodeList.getLength(); j++)
            args[j] = paramValueNodeList.item(j).getTextContent();

        return args;
    }

    /**
     * @param eachElement Element
     * @param tagName String
     * @return String
     */
    private String getTagContent(Element eachElement, String tagName) {
        NodeList nodeList = eachElement.getElementsByTagName(tagName);

        if (nodeList.getLength() > 0)
            return nodeList.item(0).getTextContent();

        return "";
    }

    /**
     * @param url String
     * @param paramName String
     * @return XssEscapeFilterRule
     */
    public XssEscapeFilterRule getUrlParamRule(String url, String paramName) {
        Map<String, XssEscapeFilterRule> urlParamRuleMap = urlRuleSetMap.get(url);

        return urlParamRuleMap == null ? checkGlobalParamRule(paramName)
                : checkParamRule(urlParamRuleMap, url, paramName);
    }

    /**
     * @param paramName String
     * @return XssEscapeFilterRule
     */
    private XssEscapeFilterRule checkGlobalParamRule(String paramName) {
        XssEscapeFilterRule paramRule = globalParamRuleMap.get(paramName);

        return paramRule == null ? checkPrefixParameter(paramName, null, globalParamRuleMap)
                : paramRule;
    }

    /**
     * @param urlParamRuleMap Map&lt;String, XssEscapeFilterRule&gt;
     * @param url String
     * @param paramName String
     * @return XssEscapeFilterRule
     */
    private XssEscapeFilterRule checkParamRule(Map<String, XssEscapeFilterRule> urlParamRuleMap,
            String url, String paramName) {
        XssEscapeFilterRule paramRule = urlParamRuleMap.get(paramName);

        if (paramRule == null) {
            paramRule = checkDisableUrl(url, paramRule, urlParamRuleMap);
            paramRule = checkPrefixParameter(paramName, paramRule, urlParamRuleMap);

            if (paramRule == null)
                paramRule = globalParamRuleMap.get(paramName);
        }

        return paramRule;
    }

    /**
     * @param url String
     * @param paramRule XssEscapeFilterRule
     * @param urlParamRuleMap Map&lt;String, XssEscapeFilterRule&gt;
     * @return XssEscapeFilterRule
     */
    private XssEscapeFilterRule checkDisableUrl(String url, XssEscapeFilterRule paramRule,
            Map<String, XssEscapeFilterRule> urlParamRuleMap) {
        if (paramRule != null)
            return paramRule;

        if (urlParamRuleMap.containsKey(url) && !(urlParamRuleMap.get(url).isUseDefender()))
            return urlParamRuleMap.get(url);

        return paramRule;
    }

    /**
     * @param paramName String
     * @param paramRule XssEscapeFilterRule
     * @param urlParamRuleMap Map&lt;String, XssEscapeFilterRule&gt;
     * @return XssEscapeFilterRule
     */
    private XssEscapeFilterRule checkPrefixParameter(String paramName,
            XssEscapeFilterRule paramRule, Map<String, XssEscapeFilterRule> urlParamRuleMap) {
        if (paramRule != null || paramName == null)
            return paramRule;

        Set<Entry<String, XssEscapeFilterRule>> entries = urlParamRuleMap.entrySet();

        for (Entry<String, XssEscapeFilterRule> entry : entries) {
            if (entry.getValue().isUsePrefix() && paramName.startsWith(entry.getKey()))
                return urlParamRuleMap.get(entry.getKey());
        }

        return paramRule;
    }

    /**
     * @return Map&lt;String, Defender&gt;
     */
    public Map<String, Defender> getDefenderMap() {
        return defenderMap;
    }

    /**
     * @return Defender
     */
    public Defender getDefaultDefender() {
        return defaultDefender;
    }

    public void init() {
        // ruleConfigLocation = ruleConfigLocation == null ? "" :
        // ruleConfigLocation.replaceFirst("^/", "").replaceFirst( "([^/])$", "$1/" );
        // ruleConfigLocation = ruleConfigLocation == null ? DEFAULT_FILTER_RULE_FILENAME :
        // ruleConfigLocation.replaceFirst("^/", "");

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Element rootElement =
                    factory.newDocumentBuilder().parse(ruleResourceStream).getDocumentElement();

            addDefenders(rootElement);
            addDefaultInfo(rootElement);
            addGlobalParams(rootElement);
            addUrlRuleSet(rootElement);
        } catch (Exception e) {
            // String message = String.format( "Cannot parse the RequestParam configuration file
            // [%s].", ruleConfigLocation );
            throw new IllegalStateException("Cannot parse the RequestParam configuration file", e);
        }
    }
}
