package kr.co.solbipos.base.service.store.tablelayout;

import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import kr.co.solbipos.base.store.tableattr.enums.TblGrpFg;
import kr.co.solbipos.base.store.tableattr.enums.TblTypeFg;
import kr.co.solbipos.base.store.tableattr.enums.TouchKeyStyle;
import kr.co.solbipos.base.store.tablelayout.service.TableGroupVO;
import kr.co.solbipos.base.store.tablelayout.service.TableVO;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static org.junit.Assert.assertTrue;

@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TableLayoutServceTest {
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
    private String xml;
    mxGraph graph;
    mxIGraphModel model;

    @Before
    public void init() {
        xml = "<mxGraphModel>\r\n" +
                "  <root>\r\n" +
                "    <mxCell id=\"0\"/>\r\n" +
                "    <mxCell id=\"1\" value=\"1층\" style=\"tblGrpFg=1;image=data%3Aimage%2Fjpeg%3Bbase64%2C%2F9j%2F4AAQSkZJRgABAQEASABIAAD%2F2wBDAAMCAgICAgMCAgIDAwMDBAYEBAQEBAgGBgUGCQgKCgkICQkKDA8MCgsOCwkJDRENDg8QEBEQCgwSExIQEw8QEBD%2F2wBDAQMDAwQDBAgEBAgQCwkLEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBD%2FwAARCAE8AZwDASIAAhEBAxEB%2F8QAHQAAAQQDAQEAAAAAAAAAAAAAAAIDBAgFBgcBCf%2FEAGwQAAEDAwEDBgMNEwULCQcFAAEAAgMEBREGBxIhCBMxQZXTFFFhCRUWFxgiMlVWcZSxsiMzNTY4QlNXWHJ1doGRlrO00tQ0UlRidCQ3Q0dzd5OhorXRJURmgoWSxOLwJ0ZIpMHC4TllZ4PD%2F8QAGwEAAgMBAQEAAAAAAAAAAAAAAAMBAgQFBgj%2FxAAxEQACAgEDBAEDAwMDBQAAAAAAAQIRAxIxUQQTITJBBRRhIjOxBnGBFULRIzSRwfD%2F2gAMAwEAAhEDEQA%2FALpQZ5pmf5o%2BJOggpqGRghj4j2I%2BJDqiFvS4BRdgPLwnCYNbEPrk26vYOj%2FUiwJec8F7nB4lQHXAdTcps3CTqaosmjK7zUlzgB0rEOrZvHhNmomJ9kUWFGY51g6XBJNVEOO8CsPvSnrKA15PEI8hSMqa6JvQU065M%2BtHFQeaeeC9FM8nGEUw8El1zJ9i3CadcJXeReCkPWE42jHWFNBaQw6rndw3kkuqZOGXFTm0rejCkNhY0D1qlRKuRiRTVDunKdbQPI4uWT3W9QWjbbdpvpObMrztG84%2FPfzobCfA%2FCfB%2Bd5yVkfzzcfu438%2BxPRjyqVGiLs2ptu4cXEpYtzAq6WXlNcojUVnodQWTkaX2st1zpoqykqI9QtLJoJGB7Ht%2FuboLSCPfU31QXKb%2B4n1F%2BkDf4ZW0PgrrXJ38UEY6ksUkTfrVX31QPKb6uRRqL9IG%2Fwy89UDym8ceRPqL9IG%2FwAMjS%2BA1rksOIWAYAC8LR4gq8eqA5Tf3FGov0gb%2FDI9P%2FlNZz6ijUX6QN%2FhlOl8Ea1yWHwF6OCrsdv3Ka%2B4o1F%2BkDf4ZHp%2Fcpv7ijUX6QN%2FhkaXwTrjyWJPQkHKrz6f3Ka%2B4o1F%2BkDf4Zeen5ym%2FuKdRfpA3%2BGRpfAa48lhTnxrx3Hiq9Hb3ym%2FuKtRfpA3%2BGXnp9cpv7irUX6QN%2FhkaXwGuPJYMjKQ7pVfvT45Tf3FWou32%2FwyzOwjlC3rbBqnVmj9R7M5tH3PSJhZVQTXLwp%2FOPc8FhHNM3S3c8ZzlVaa3BNPY7G5IcE49NuKqXQ07pSUp3jSCR1KGSeHpSgQkE%2BNG8AgBzggY602XlAL3dGVFFrHd5o4ZXhkaENpnu6ThOtox1nKjx8h5GmzEDAGU%2FA%2BcvBbGAM9aejhjZ1BLDgOtVbRNEtvEAnrS2nITEErXjd6z0J5oICS15GIz9r4UTPfKlHpUe2D%2B4owpBC1w2EPcVF88CaccJ2L54Ey454JOXcvj2E54kLxGQUZHRlJY00SJz3QxguPsR8SVuEjjlS6emAhj4fWj4k6acYytekz6jE7267dynWjPQmqphZLjGMp2A5wiibFiJxSxTkqRGzPFPtYB1KyiiupkJtMekpwUw8Sl4ARgeJTSIsjtp2jqS2wtA6E6vUUAgRN6MJXNgdS9RkqSDzdHiRgL1CAPMYTgBwkJQJx0qUQxXA8Fwvlt%2FUy6y%2B8pP2uFdzHTlcM5bf1MusvvKT9rhVSUdj5O31P2zL8TrL%2BxRLoS57ydvqftmX4nWX9iiXQlvjsjkz9mCEIVioIQhAAhCEACEIQAIQhAAqP7AnhnKu5Queu6w%2FrZ1eBfP7ZjrbRujOVVt6k1hq2zWJlVd2Ngdcq%2BKlEpbJLvBpkcN7GRnHRkJGf1NfSezLaPqGjo4ph0xd0BaKNuOxQ%2FwCOHRH6Q0neL0bcNifXti0R%2BkNJ3ixnQSN2L3HpKACVpY24bEftxaI%2FSGk7xKG3LYiP8cWh%2FwBIaTvFFk0boInHp4JbYG9a0wbdNiA6dsWh%2FwBIaTvF47btsS6tsOh%2F0hpO8UW2TSN6bEwdQ%2FMlhrG9a0D0%2BNin24dEfpDSd4vDt32KHp2xaI%2FSCk7xVpk2dBMjB0JJn4YAXP8A09dif24tEfpDSd4j089if24tEfpDSd4poizfjOTwXm%2BTjitD9PPYljI2xaH%2FAEhpO8Sm7ddiX24tD%2FpDSd4ooEzf2PIIdnoWUjO83e8a5gNuuxED%2B%2FJof9IaTvFkKXb5sN5kb%2B2fQgIHXqOj7xUki6Z2C38KSM%2BRSFzmi5QmwNlJG123DQAIbxB1LRZ%2FWJ08obYFn%2B%2Fjs%2F8A0mou8T4bCZeWdDhGZBlMOwAfeWjQ8obYCH5O3LZ8OHXqai7xJg29bC7hUw0VDto0JU1NRI2KGGLUdG98j3HDWtaJMkkkAAcTlJybjMexvDugFeEL13sQknpSGNRrkA%2BYx%2Fej4ks4x0JMLjzMf3o%2BJKyt5ksxlwj45wmqYHIUyuZluVGpfZKpb4MjGMcE4kDqXozlWRUUhCFIAhCEACEIQAIQhAAlDoSUvqHvIQNWeLhvLb%2Bpj1l95SftcK7lkDrXC%2BW08HkzaxAP1lJ%2B1woBKjsvJ2%2Bp%2B2ZfidZf2KJdCXPeTt9T9sy%2FE6y%2FsUS6Et0dkcmfswQhCsVBY3UN7i09aJ7rLC%2BYQgYjYQHPJIHDPizk%2BQFZJct211VUW26iETxTAulc%2FHrXP6APfAz%2BdWhHVKimWWiLZ0WO608lpbd%2BiMw87ukjIOM7viznh76Z05fYNR2qK5wwugMm9vQvcC5mCRxx48ZXKLMa%2B4bNLhb20Uz4qesjma8NO6Y85eM%2F1d0k%2FfBZ%2FYtSTQ01zqXwvbHNzLQ8g4c5rpd4DygFv5wryxpJsXHK5SS5OloQhKHghCEACoPsk2FbLduHKs2%2B0u03TAvEVou8clG01U0PNuklmDz8ye3OQxvTnoV%2BFUXkmfVZ8o%2F8KU362oWXq3UPBr6X2Zv3qBeSh9q1va1b3yPUC8lD7Vre1q3vlYRC5up8m4r36gXkofatb2tW98j1AvJQ%2B1a3tat75WEQjU%2BQK9%2BoF5KH2rW9rVvfI9QLyUPtWt7Wre%2BVhEI1PkCvfqBeSh9q1va1b3yPUC8lD7Vre1q3vlYRCNT5Ar36gXkofatb2tW98j1AvJQ%2B1a3tat75d0uN9t9slZBVSO33jOGtzgeMqdHIyWNssbg5j2hzSOsHoKjW%2BQK%2B%2BoF5KH2rW9rVvfI9QLyUPtWt7Wre%2BVhEKdT5Ar36gXkofatb2tW98j1AvJQ%2B1a3tat75WEQjUwK9%2BoF5KH2rW9rVvfI9QLyUPtWt7Wre%2BVhEI1PkCvfqBeSh9q1va1b3y4LyreTVsX2KS7Lb%2Fs00eLRXV2urdRzyitqJt%2BLJfu4le4D1zWnIGeCv8qn%2BaBfQzZF%2FnEt3yXqYt2StyyjulJJCU4ptLbo1JGAgaeZj%2B9HxJe6kw%2FOY%2FvR8SWeC3mOiNWgc0feUClPryp9b87KxtM7dkPvqj3LIyzR0JXAJtrwcFObwVkVPULzeC83wpAUhNmQeNIMwQA%2FnhjC8JAUc1ACbdU%2BVQBLLwF4ZAOtQHVPlTLqo%2BNFgZJ07R1pD61oHArGGpz1ppz3O6yiyaMg%2Bv8q4Xy0Koy8m%2FV7M9LKT9qiXY8E9S4tyymkcnDVx%2FqUn7VEosmjv3J2%2Bp%2B2ZfidZf2KJdCXPeTt9T9sy%2FE6y%2FsUS6EuhHZHHn7MEIQrFQWB1rZjebE%2Bnhoo6mpbLCYg5oJaOdYX4J6PWg58YWeQhOnZDWpUzXqSzSUuhzaYKJkVVJbjG%2BJga3emMW6c44Ek9aa2fWm4WawOpLlTmGY1Msm6SD60ngeC2ZCnU9iFBJpghCFBYEIQgAVReSZ9Vnyj%2FAMKU362oVulUXkmfVZ8o%2FwDClN%2BtqFk6v9s19L7MuEhCFzDaCEIQAIQhAAo1yiqJqCeKkfuzOjIYfKpKEActmilglfFOwtkYSHA9IK2PRdNWiofV8W0zmFp48HOyOr%2F6qTLYa2W5zVLqdjo31kcoJcDlg3t74xwWT09Q1FvtraaqYGyB7iQDnpKXGPks2ZNCEJhUEIQgAQhCABVO80D%2BheyL%2FOJbvkvVsVU3zQU7tp2SOxnG0O3nH%2FVerR3LR3LK4yOKOjoWOfeGxZ34JRhI8%2Fqb%2BY%2F8wVHFmtNECH5zH96PiTmcDpTMLwIY%2FvR8SrxyvNf7StJv2fWTZlqoWKu1Tfhapah1PHK3D9xrS4Pa7gC7PDitzdGNK%2FBYCul9YRlY%2BDi7K4I%2FYRy2JBh%2FKXsR%2FwCyGd0kt2CctVnseUrYh%2F2Qzukl5oXuOWGfBY1r8JXOjoyq5ekRy2PumLF2QzukekPy2Pul7F2QzulHfhyR2J8Fi3T460h1R5VWqi2GcvO8T1rLJt3stTFQVHgskz6GCIOk5tjzugxkkAPaMkDjnq4mT6m%2FzQo%2F469P%2FB4O6TVK1aKaa3LDuqCeOU0%2Bo%2FrKvnqbfNCT%2Fjq0%2FwD6CDukeps80I%2B3Vp%2F%2FAEEHdIsKO%2Fmc%2BVJMrnLgXqbfNCPt06f%2BDwd0vfU3eaEj%2FHVp%2FwCDwd0gKO9Ye49aUIj1lcE9Th5oX9uvT%2FweDuUepx80L%2B3Xp%2F4PB3KgKO%2BiIe%2BgNaOhcC9Tj5oX9uvT%2FwAHg7leDk3%2BaFDo216f%2BDwd0gk7%2FwAPEVxPlmfU36v%2B8pP2qJY31OHmhf269P8AweDuVhtYckHl06%2B05V6S1btZ07X2quDBUU5iiYH7rw9vrmxAjDmg8D1KEgLTcnb6n7Zl%2BJ1l%2FYol0JUz0%2FydvNENLWC26YsO26wUtstFHDQUcAp6dwigiYGRsy6Ek4a0DJJPBT%2FSU80l%2B3zYPglN3K2LqIpHPl0k227LeoVNrrsu80OsFNHX3zlA2aCkfU09I6WO300hY%2BeZkMZLRCCRvyNBx0A5wp3pUcu%2F7qOx9ixdyqy6vHHwyY9Dll5RbtCoPoW%2B8s29bedQbIqnbxbaqTRsVJcK50ttijgroZOacYmuZDvsy2TBPT04VpsbafHpX4fW%2Fuo%2B8xLcldBlZ1JC5RDHtvZJO6WfSr2vkDox4dWesbutGPYfzg49fT%2BRY3Vtt5Q9307W23TGodL2O6Tta2nuDaiqmNOd4EuDJGFruAIwR19XSo%2B9xE%2F6fmO0oXz%2FANj9x5bO2GbVkVq5RVuoDpK%2Bz2Kc1Fogdz8kR4vbuw8Gny8V0X0qOXf91HY%2BxYu5Q%2BtxLwyF9Pyvyi3aFUT0qOXf91HY%2BxYu5R6VHLv%2B6jsfYsXco%2B%2BxE%2F6dmLdqovJM%2Bqz5R%2F4Upv1tQvPSo5d%2F3Udj7Fi7lbLyYdgu0TY1rXXOutoetbZqW6a1NPJPNSwOiPOsdI5znDAbx5wcAOpI6jqseSFIfg6LLjfks8hYvz3l%2Bwt%2FOjz3l%2Bwt%2FOsPciaft5mUQsX57y%2FYW%2FnVbNbbPOWbdtX3i56N5RVns9jqqyWW30ElpjkdTU5cSyMuMRLiBgZyVKnF%2FIPp8i%2BC06FT70rOXl91JY%2BxYu5R6VnLy%2B6ksfYsXcq2qPJHYnwXBQqfelZy8vupLH2LF3KPSs5eX3Ulj7Fi7lGqPIdifBcFCp96VnLy%2B6ksfYsXco9Kzl5fdSWPsWLuUao8h2J8FwUKn3pWcvL7qSx9ixdyj0rOXl91JY%2BxYu5RqjyHYnwXBQqfelZy8vupLH2LF3KPSs5eX3Ulj7Fi7lGqPIdifBcFCp96VnLy%2B6ksfYsXco9Kzl5fdSWPsWLuUao8h2J8FwVU3zQUb1p2SN8e0K3j%2FZeoHpWcvL7qSx9ixdyuOcojR%2FKQ01X7NKzbPtit2r7ZJra3spKWmt7IHQ1G8TzhLY25G6HDGetWg4uXhh2px8tF%2Bq6mipLc9zW7zn4GSejisDw8QWZvlY6VkdO%2FAPB5xwWH6ePH8ybKi0CHHVfMWcfrR8SrXyu5uc1jsTAPRram%2FWRKw8DHOhYf6o%2BJV05W0e7rHYmSenW1N%2BsiVpPwLS8lyEIQuYdRAhCEANacdINOamEc0sRk1BFGXRSOjeGubStdhzSCOBIyDlbp6EbT%2FSLv2zWd6udTWi7MZWU9r1JUUVNXVsVfLF4PFJiVnN8Guc3IaeabkcTxdgjIxlfPTWvurb8AiW2GaCikYpYJttm4ehG0%2FwBIu%2FbNZ3qPQjaf6Rd%2B2azvVp%2FnprX3Vt%2BARI89Na%2B6tvwCJW78CPt5m4ehG0%2F0i79s1neo9CNp%2FpF37ZrO9Wn%2BemtfdW34BEjz01r7q2%2FAIkd%2BAfbzNw9CNp%2FpF37ZrO9R6EbT%2FSLv2zWd6tP89Na%2B6tvwCJHnprX3Vt%2BARI78A%2B3mbh6EbT%2FSLv2zWd6j0I2n%2BkXftms71af56a191bfgESPPTWvurb8AiR34B9vM3D0I2n%2BkXftms71HoRtP9Iu%2FbNZ3q0%2Fz01r7q2%2FAIkeemtfdW34BEjvwD7eZuHoRtP8ASLv2zWd6j0I2n%2BkXftms71af56a191bfgESPPTWvurb8AiR34B9vMY1owwaIudJz9RLHTaqtMcRnnfM5rfPCjON55LiMk9JUhYeez3m4RT0l21PPU0lTcYLnLC2mijzLC%2BORgDg3IbvwsJHSeIzgrMLNmmptNGjDjcE7Kt7Jvq79sv4Dtv6qmVpFVvZN9Xftl%2FAdt%2FVUytIqT3ReGzBCEKiLlX%2BRB%2FLNs%2F8AnDuPxqyTL3b5aN9yhfPLQxh5fWRU0j6ZoaSHEzBpYA3Byc4GDnoVUOS5UVFJpPlE1dHK%2BOeHVt8kiew%2Bua8RPII8oOFfSm1rpWjpoqOl1HZYoIGNjjjbVRBrWgYAAz0ABaViWSTszPK8cUkjR2PZIxskbmua4Za4HIIPWFGiuVNUPmbRx1VZ4O8xTOpKWWdsbwMlrnRtIDhkZBORkJFoNK6mmNBzXgorasQczjmxGKiQNDMcN0DGMcMYwtt0Pq%2FTVt0bZKNt%2Bs9MWUEBkj8JiYRIWAvLhn2RcSTniSSTxVMeJSbTexfJlcEmlua1R1lJX00dZQ1MVRBM3ejlicHNcPGCOBTqx9BWwV9%2B1RUUlTHNTOvDjC6J4dHg08DnbpHDi9zycfXFx6crIJUlpdDIvVFMEIQoLAhMUc9XcoPC7ZZrlVUxc5rJ2U5bHJg4JYXkb7fE5uWnqJTFocNR6itlDFXVVLSVFFWTvEbWNeXxyQtAO804xvv6FdY5NpFHkilZOQth9AVJ7eXX%2FvQ92sdqLSUdpsVfc6W93Iy0tO%2BVgeYi0kDIyOb6Ex9PIWuoizHoTME9TWTVENvtVbV%2BCyCKV0TButeWNfjJIz617T%2BVJpK2KsM0YjnhmppDDPBPE6KWJ4AOHNcARkEEHoIIIyCCkuLStjlJN0SEJisrYKFjHSiR75ZGxRRRRukklkPQ1jGgucek8BwAJPAEpU766jMZr7PXUrJJBEJJY2hocegHBOEKLatA5JOh1Cl6X0y2%2BWk3KrvNwZI%2Brq492IxBrWsqJGNAywng1o6Sst6AqT28uv8A3oe7Tl08mrEvqIp0a8hJv9LFpi41HOXGrqKWKhFU%2Fngxxbhz94jdaCeDRw49CZr6mptVJJcbrZ7hSUcA3pqiSHMcLet7y0ndaOkuPBoySQBlLeOSdDI5ItJkhCAQRkHIPQUKhcFVvl3%2FAEP2T%2F5wLf8AJerSKrvLsGaLZKP%2FAOQbd8T0zF7opl9GWfqYWzy5MQPAAEjqXgpISOMLT%2BRT52jg7CZwPEnyfkzRRo0DcQx%2Fej4lXDleAjWGxLh%2F7703y4lZmnh%2BYx%2Fej4lWzliM3dXbET49b03y4lpkvDM6dtFvUIQuWdRAhCEACe0fpq16gpa%2BuuprpJWXCaFvN3Coia1jcYAax4aPzJlZvZr9Crl%2BFaj%2FAO1P6dJy8iOobUfBK9L3TH2K5drVfeo9L3TH2K5drVferY0LZojwY9cuTXPS90x9iuXa1X3q57A6WeyW%2BGSonImudJSveJnNkMTq1kbhvg73FhIznPHpXZVxmi%2BhVq%2FDdF%2FvGNJyxinHx8jsUm1Lz8HQ%2FS90x9iuXa1X3qPS90x9iuXa1X3q2NCdojwJ1y5Nc9L3TH2K5drVferAar09bdOy2Wa1GsY6ruDqeYS108zXR%2BDTvxuyPcB65jDkDPDp4ldCWn7R%2FwD3d%2FC7v2OpVMkIqL8DMc5OatmH0bp%2B3ajqdRTXd9bI6kuraaAR188LWR%2BB0z90Nje0eykec4zxWy%2Bl7pj7Fcu1qvvVjNmXz3VX4bb%2BwUi3ZEIRcV4InOSk%2FJrnpe6Y%2BxXLtar71Hpe6Y%2BxXLtar71bGhX0R4Ka5cnJ7IXinq4XSyPbT3O4U0ZkeXuEcdXKxgLjknDWtGSSeHFZBY%2By%2BwuX4auv7dOsgudP2Z0YeqNC09sY0tprazqbbJQV90kvWqqWCjrIJpYzSxsiaxrTG0MDwcRtzl7uvoW%2BoQqt3uWpIEIQgDnWxPYpatA6u13aNKXqqEN7qafU1WbjG2od4VWS1TZGR7nNhsYEDcAhzuJy48F1z0CXH27o%2Bzj3qxegP74WpvwNZ%2F19wXRVuxwjKKbMOScoyaRqA0RdQMC%2FUuP7A7vUn0CXH27o%2Bzj3q3FCv2ocFO7Pk5vqqzXfS9nbdYrpRzgV1DTOi8BczLZqqKF2DzpwQ2QkcDxAQs1tV%2Bk%2F%2FtW0%2FwC8KdYVZc8VFqjVgk5J2CEISB5smndb6Qk0tZqd2pLbDPSUcVPUU81SyOWCVsbQ5j2OILXAjoIWkbOpY59T2aaGRskcluurmuactcDUwEEEdITFXftJeEPirK%2BgkmhdzbwcPLHDpaenBGejyrKaRqaKs1rZ6m3SxSU0lprjE%2BIjdLecpujC1rI5yiqMksahFtM6csLrX6Ubx%2FY5fklZpYXWv0o3j%2Bxy%2FJK0vYzLc1vQ1%2FsNsqdQU9yvdBSSm5sdzc9SyN2PBYOOHEHCiXi6WvUGubpfLDVwVlvdQ0dF4TA4OimqI3zukLXDg%2FDZYmlwyMtLc5aQMRX3jSsNZJTXCpovCYsCRr2hzm5AIB4cOBB94hZC33C33OlbV2usgqacktEkLw9uQcEZHWCCCOojCxTyNw00bYY0p6rHqS722w6lst4vNSyloYZpmTVUpxFT70Dw18jjwY3Prd48AXDxrLa31Jp25UdPT26%2FW6qlfcISyOCqY9zhvZyADk8FhK2uorZSyV1wq4aaniG8%2BWZ4Yxo8pPALGUd40nLVxQ0VTQ%2BEPduxhrAHE46Bw6cZRDI4wcaJnjTmpWb9s9%2Blhv8Abq%2F9rlWyLW9nv0sN%2Ft1f%2B1yrZFsj6oxT9mc22pOa110e9wDRY3kkngB81W5naBoa2U89XW6otbo2xn5nHUMlklP8xkbSXSOPQGtBJPAArWdbTU9NfZKirexkEVta%2BV7zhrWB8hJPkwtYpr%2FpBk7G09fb4pZHCNh9awuJ4BoJxxJ4AdaRLI4TdIfHGpwVsn2SnlpLNQUs7NySGmije3OcODACFMQhYzYgVXeXZ%2FI9kv8AnBt3xPVolV3l2fyPZL%2FnBt3xPTMXuimX0Za5xJ4FI60o56l5jPHKa%2FJnWxq8EYEMfD60fEqz8stuNW7EPx4p%2FlxKzcPzmP70fEqycsz6bdiH48U%2Fy4lvn6sxx9lZbZCELjnXBCEIAFj7bqLWelG11NQacsNbRzVb6mOoqb5LTPw8Dg5gpXgYII9kc%2BToWQUergt1VPb6a6x00lNNcaWN7ahodG4ulaGgg8DlxaB5SFfFJxl4KZIqUfJ76Z%2BtjwGkdKfpTN%2FBJcm0rXkDtybRul43Yzh2qJgcfloVn9f6C0pR6G1BUDTdjicy2VW48UcYIeYnBuDu9OcY8uFj9F2HTd%2BvN6pbnbLXWV0Pg84bVU7JJBA5ha1w3gfW78cg98FbHrTSsxrQ43RAj2la8nduQ6N0vI7GcN1RMTj8lCsN4BfKfT1GynpaCW501XTVpgfVPZAXR1LJnM50Rl2MNIDub4nHAdWf1rpyxWPV1gjtVot1JUOpK6STwWmbG7m8wgFxaBwLjwB6cHHQcJWfLKUZU2aMUYuNpDUm0zXUTzHLo7SzHjpa7VEwI%2F8Akl7HtJ15I1zotG6XeGDLi3VExDR5f7h4LL6I0ppy%2B%2BfVVV2W01lQy4hkj56Zkkg%2FuaAgElpPQQtc2hWawWWvv1HQW%2B3UzBZYH1UVNA1o3d%2Bo9m1o48AeHi99Obmo6rEpQctNEn00Nbe5LSn6UzfwSYrb7qzVNRbPPbT9lt9JQ1LqvnaS8S1b5DzMsQaGupowB81zne%2Btxg54dEj0Bo6aBtVDpvT74XsEjZG0cRa5pGQQQ3BGOtcx0iyBlhgdSxsZTTSTzU4YA1vMvme%2BMgdQLHNICrmcox8sthUZS22HbZe9Y6SrL063afsVdRXGuZWsmqr1LSvZ%2Fc0ERa5gpZB7KIkHe4gjoUz00Nbe5LSn6UzfwSj3mnpKuCjp6%2BKGSmfc7fzrZmh0ZZ4XFneB4Y99bxqPQ2jLbp%2B53Gr05YIIKajmmkldRxAMa1hJOd3hjCnE5yj4YZVCMvK3NSk2la7hIE2jdLsJGQHaomGR4%2F5EiLaVruZ25Do7Sz3dOG6omJ%2FYl5s907ZLpd57fe7Zbamris1BzUdXCyR26x0wkLN4HIBczex0bzc9IUnaBpex2S96XNqs1tpKl1VUyvNLTMjfzLad7XEloHrQ%2BSIe%2B5qm56NVldMNemjGWOnr6eilddIqeKqqayrrJI6eV0scZmqJJQ1r3NaXYDwMlozjoU9CFibt2bUqVAhCEACEIQBGsNdU6d1heLvLaKurprhbbfTROpnRZEkMtW54cHvaeidmCM9fiWz%2BmBF7mLz%2Bel75aLruaqptD6hqKGWWOpitVW%2BF8RIe14hcWlpHEEHGMLo7dm2m3tD2W2pLXDIIr58Ef99a8MpyjS%2BDJmjCLtkL0wIvcxefz0vfI9MCL3MXn89L3y1a2xiCKopmF5ZBW1kLN95eQ1tRI1oySScAAdPUti0poGyXLS1muNVQ1U01Vb6eaSQ10%2Fr3OjaSeD%2BslWhOc20VnCEEm%2FkxOs9RTalsjbRRaduUUj7hb5y%2BZ1OGNZFVwyvJ3ZSfYsd0A8V4sfQUooL3qS2Rul5ijuvNQMkldJuMNNA4tBcScbznHp6SVkFmyycpeTTigoxtfIIQhLGG76TdQO0Tp020w%2BD%2Bd0IbzWN3O40O6OveznrznPFc32ffTXaf7Bdf2mBenT1lMkkrbdCx0zzLIWDd33npccdJPWekpy3RS2G%2B265Wu3wPpaOjqaUwc7zW7zjoXNI9aQR8zdn3wtSzKTXwZey4xdHVFhda%2FSjeP7HL8krF%2Bji4%2B5%2BP4b%2F5FBvuprneLNWWplkhidVwuhDzWZDd4YzjcTnlhW4lYp3sTdnPs9QfhRn7JTrGanbTN2kXrwEMETqGhNTzfR4Zmbf3v6%2FMimz17u4sZNZLVUTyVUtEwyykGR4yC4gAAnHTwAH5E9RUNFboPBqClip4t5z9yNoaC5xy4nHWSSSekkrLLKnDSao4mp6rJNqfb2az02bk6IMNXKIDL7HnzTybmM8N7G9jy9HHC2TaJ9DIfwjD8panWUdJcKd9JXU0c8EmN6ORoc04ORwPiIB98KPFZLVDMyeOiZzkbt5pJJwfHxKI5VGGmgnicpqVm5bPfpYb%2Fbq%2F9rlWyLn2nb%2Fc7DbTbXWeGcNqamVsgq93LZJ3yDhucDhwz5Vk%2FRxcfc%2FH8N%2F8i1RywSSszSxzbfgwm1D55c%2FwI%2F45V0asbZX2m5M1H4L51OpJBW%2BFloh5jHr9%2Fe4bu7nOepc4u1TPqC4zT3C2QxU8lI2mMfPc7zg3nlwI3Rww4D86g%2Bh6yksc%2B3RP3HtkaHguAc0gtdg8MggEHqIBSXmUZMasLlFC7Hz3nLb%2FAAgP53wWLnOczvb24M5zxzlTUIWU1IFV3l2fyPZL%2FnBt3xPVolV3l2ECi2SknAG0G3fE9Mxe6KZfRlrSQOlebyhyXCBvAPBKaNwB9j0Jyg2ZnJIxcPzmP70fEqycsz6bdiH48U%2Fy4lZqFx5mP70fEqx8s3nfRXsREED55PRvT7kTC0Oed%2BLDQXEDJ6OJA8q3T9WZIvyi3%2BnNK2fVlTc5tQ1d0dBRVDKanpqOumpGg80x7pHPhcx7iecxguLQGjAySVFv1st2lNU22w2R9c6iuVvq6osrKuSpdFJBJA3g%2BVznkOFRxBcQNwYxkrEm37QoqmoqLZYNZW5tS8SSRU1XaNwvDQ3e9fK4g4a3r6kmKz65ddYbzdNL6uudRTU81NB4VV2rdjZK6Nz8COZuSTDH056PKVia%2FRp0%2BTYpfr1X4MxY7TQ6r1RX2a8z1rKC20FNUmKjqZKd80k8kzQXSRua9oYKc4DXDPOHPQE9qrTdm0fUWR%2BnJrk2K41z6Opgra%2BesaR4NLKHtdM972uBhAwDukOdkZwRgpbVrkXKW62zS2rrZUVEEdPMaWrtWJGRue5mRJM7oMr%2BjHsuPUvHWrX9TV0lTddP6yuQoZTPDFU1doDBIY3x73rJWk%2BtkeOnHFQlUNLXkHK56r8GbSJoIamJ8FRCyWKQbrmPaHNcPEQelN0NW%2Bsp%2Bdlo5qSRskkUkExYXxvY8scCWOc08WniCQn1l2Zr3VkFlhsbHskZZqFr4nB8bhTsBa4dBBxwKeqrdb65zHVtDT1BjzuGWJry3PTjI4KQhFsilsR6W226hc91FQU9OZMb5iiawux0ZwOOFIQhG5OxFqbVbKyXn6u3Us8m6G78kLXOwOgZI6OJ%2FOnKShoaBro6Gjgp2vdvubFGGBzujJx0nAH5k8hFvYKW5j3aesD2bj7Hb3NPUaZhHxKeAGgNaAAOgBeoQ22CSWwmSOOaN0U0bXseC1zXDIcD0gjrCh%2BcNiDmOFloMxuDmHwZnrSOgjhwIU5Innhponz1ErY44xvOe84AHjJUx1N1EhpbsbqrfQV254bRU9RzZJZzsbX7uenGRwSaW12yhkfNRW6lp5JAGufFC1hcB0AkDisPT3HUOqXkaXpo6ahGQbjVtOH%2FAOTZ0n3zw95ZGPZ1Rzjfvl8ulykPSHTmOP8AIxvR%2Bdb10Sxr%2Frz0%2Fjdiu4m7irMghQzsz0l1UtS09RFVJkf61Hm0JcqAGTTeqa2Fw4tgrDz8R8nHi0eUZR9r08vEMnn8qv4bDuSW6MohYCm1HV0Nayz6roRb6uThFM05p5%2FvXdR8h%2F8Aws%2Bsufp8nTupr%2B3DGRmp7AhCEksDmte0se0EEYIPQQmohdKa2ectLqS7wUAjMLYWVPrmRn61spHOtwOAw8EDGCMBOoUqTWxDipbjVLS01FTRUdHCyGCFoZHGwYa1o6AAvaBtwtEElHZb5cKClkkfLzEMjTG1zjlxYHtO4CcnDcDJJxkkpxCFJrZg4p%2BGMUVFBb6cU0BkLd5z3OlldLI97iXOc57iXOcSSSSSSSn0IUbk7AhCEACEIQAIQhAAhCEACEzUVEsclPS0tHNWVdZKIaenhA35HYJPEkNaA0OcSSAAD5AXa%2BlvlpbHLdLQIYpJmwlzahjy1zuAyB5VZQk1aRVzinTZ6hT9H6XtF2snh9wFbLO%2BsrGlwr52jDamRrQA14AAAAwB1LNegXTf2Ct7Sqe8Tl07auxL6hJ1RqyEjU9LQaWuNVPSMqjBFbhUvifUyS5LXSE7vOOOCQMdXUnK6g1NbqOa4T6bnmgp285MKSVs0rWD2ThGPXPwOO60Fx6gTwS3ikm0hiyxaTZ4hJiljniZPDI18cjQ9jmnIc0jIIKUljAVWeXp9C9lP4%2F0HyXq0yqzy8%2FoXsp%2FH%2Bg%2BS9Mw%2B6KZfRlhogSRkqbGzLRwUaJvX1eNPGqhZ60yAnyLfRzmMQ%2FOY%2FvR8SrZyu%2Fp72Efj7SfrIlZCF3zGPj9aPiVbOV29jNdbCXucGtbrykJJOABzkSdLYWty%2FaFF89rX7ZUv%2Bmb%2FwAUee1r9sqX%2FTN%2F4pZclIUXz2tftlS%2F6Zv%2FABR57Wv2ypf9M3%2FigDndP89r%2FwAJV37TInlHo5I5X1skb2vY65VxDmnII8JkUhcyfszpw9UCEIVSwIQhAAhCEACEIQALntzvTtU3sU8bQ%2BxW6Yc60nHhkjekZH1o%2FwDXk2HXl0mtmnZm0jsVNa9tJB98%2Fh8WVr1BRQ26jiooPYRNxnrcesnyk5K7v0zBHHjfUS3fhfjl%2FwDBmzSt6EdVtdZRVtFHJQBrYWtDQxoA5vA9jgdGFLXNLTdqm0VIngOWnhJGTweP%2BPlWz3vaHpHTdhOpL%2FeIaGkadz5pkyF%2F8xrBkud5ADw49CTPos2TKoYk5OW1eWGuKVvwbIhcXh5WeyySs8HkgvsMOceFSUI5rHjw15f%2FALK6zZL7Z9S2yC9WG4wV1FUt3o5oXbzXeMeQjoIPEHpTOu%2Bi%2FUPpkVPq8UoJ%2FLXgrjz4srqErPbxZ7ffbfLbblAJYZR%2BVh6nNPUQtPs9TcLLdHaSvkvOyMZv0NSRjwiIdR%2FrD%2F14zvy1faHa5KyxG6UYxW2h3hkDx04bxe33iB0deAkdNJZV9vk2e34f%2FwBuXl%2Bl60SkJi31kVxoaevh9hURNlb5MjOE%2BuZKLhJxfwaE7VoEIQoAEIQgAQhCABCEIAEIQgAQhCABCEIATT3WOwagtF8qqaolo6aaVlS6nidK%2BJj4XgP3GgucN7dBDQT67OMAqZqzWmm7%2FBT0For5Kid9dE8NFNK31oJJJLmgDh41ja%2B4U1uhbLUGQl7xHHHFE%2BWWV5BIayNgLnuwCcNBPA%2BJMeezmTRxVVlvNKJXiNslTbpY4w49ALiMDo60%2BE5KDSXgTOEXNNvybns9%2Blhv9ur%2FANrlWyLW9nv0sN%2Ft1f8Atcq2RbI%2BqMU%2FZnN9p7XOluTWMc5xsjwGtaSScy8ABxJWys2q6QpIppqaesr5xGeapaaimMkz%2BpoLmhrcnhvOLWjpJA4rC64q4aC9S11SXCKntglkLWlxDWvkJwBxPAdAWBqL6yjhfVXGzXujpoxmWontc7YoW9bpH7uGNHW52GgcSQEiU5Qm9KsfGEZwVsk2mlkobVR0U2Ocp6eOJ26cjLWgHH5lKQCHAOBBBHSELGbAVWeXpwteyo%2F9PqD5L1aZVZ5ev0K2Vfj9QfJemYfdC8vozvplkd0uIHkSC4Dggu8ibe47y6LdbHOXkmQu%2BYs4fWj4lWbllMZPqrYnDLG17H62p2ua4ZDgXxZBHWFZaFx5mP70fEq08sQ51dsR%2FHem%2BXErz9WRHdFoqDZ%2B%2B%2F1Nc60W%2FS1JT0c7afdqbTzr3HmmPLste0AevxjHUo1XpGOwalt9jvVn01Vx3Ghq6tklLa%2BZdG6CSnbg7znZB8I8mN3rytltl81Dpeetks9qtl1gr5GTOgrKp9K6KQMDC4SNjk3gWtZ60tGCCcnOAzXVl11Je6fUF7t1DQS0VLLSU1NRzuna1sr43SOdI5jN4kwxYG4Mbp4nPDnuUNH5NyU9f4MHS6Oiv1%2FrbNZLRpqkZb6OlqpH1VrEpkMz52gANc3GOY8ud7qwlXXQsmm6u1ee1t0tV09yrHUZbT2nmnsIp5ZQ7LnuB%2BdYxj67p4LN0FZddOXqe%2F2Sio66SrpY6SppKuZ0LJBG9zonCRrHlhbzsuRune3h0bqVd7xqDVtVbpbzabZaqe1zvqo4aOqfVOmlMT4gXSOij3WhsknrQ05JacjGCJw0fkGp9zxsJp6amo4W01JBHDCzg2ONga1vvAcAnEIWc0AhCEACEIQAIQhAAhCEAaXtAcZLtp2kd7B888p99keR8aZT20RphrtP3A5DIqp8BPVmRuB8SZXqOn%2F7TE1w%2FwCWY5%2B8gVYNqWoKjUWuLkamUmmtMz6GmjJ9bHuHD3Y8ZcCc%2B94grPque1XS930brGTVEVIfO251Yq6aqfA2SFtSCHOjeHgsJ3hvbrgQQeg8V73%2BgMmDH9Ql3K1uL03ycz6mpPEq2vyZu%2FcnfWdg2Q0O1Wqa4887nqy3c2RLSUb8c1M743Nx61r2k43XYmclrVtbZdoXoQEzjbr%2FAAyvEBPrWVETC8PHiyxrgfHw8QWLquU5tyrKSWkrdoJnpZ43RyxyWq3uZIxwwWuBgwQQcELZOStoG43LVDtolXTPhtlthlp6GR7d3wid43XOZ42taXAnoyQB0HHqvrT63B9C6uP19wd3o0v5ey8pbPZ%2F%2BTn9Npl1EH09%2Fktam54WVEEkEgy2VhY4eQjCcUW6VjbfbKuve7Ap4Hyk%2FetJXz3jTc0lvZ6l7eTS9ASPk0jQF5yQJG%2FkEjgP9QWwLCaKpjSaWt0Thgui53H37i7%2FAO5ZtV61p9Tka5f8l8XogQhCzFwQhCABCEIAEIQgAQhCABCEIAEIQgAoblb7Nqix3S7Tsp6OKoljfUScI4Xvhe1hc48Ggk7u8eGXAda2LaBUU81sg5qeN%2B9cICN1wORvLXHNa9pY9oc13AgjIIUaG02qnlE9PbKWKRvFr2QtDh%2BUBOhl0x00Jli1T1Wbds9%2Blhv9ur%2F2uVbIud2W7X6x0Jt1M2gliE88zXSNeHYkldJg4OOG%2Fj8inei7Uv8AR7Z%2BaT%2FitMc0El5M8sM27SMZtR4PuZ%2F%2FAGR%2FxyroFZqPT1hoaivvtxp4qUM3SHHfMpPARtYMl7ndAY0EuJwASVz6sqLhdrg%2BuujaXdNO2nEcTXYIDnEk7xOc72MKPDarXTSCamttLFIOhzIWtI%2FKAkvOoybQxYHKKTE2SGWmstBTVDCyWKliY9p6WuDACPzqYhCzbmpAqs8vXhatlR%2F6fUHyXq0yqvy9%2BFo2V%2Fj7QfJem4f3ELy%2BjO8l3DKGN3xveVROcceGVOgG7EB%2BVb5s58ELhd8xj4fWj4lWrlhEnV%2BxH8d6b5cSslC75jHw%2BtHxKtnLBOdX7Evx3pvlxK0%2FUrHdFv0IQuSdZAhCEACEIQAIQhAAhCEACEIQAIQhAGH1dYzqCw1NvjOJ8CSB3QRI3iPez0Z8q0%2BzXHzyomyvbuTsJjnjIwWSDpGP9fvFdIWm6k0zUUdzOprLEZA%2FjX0beBnaPrm%2BJ3%2Frj0HtfTOqi4Ppsjr5T%2FPH%2BTPmg71om2GwzXibedllMw%2Bvf4%2F6o8vxLda2yWi5Wt9luFspqqgkZuPp5og%2BNzfK08D%2FAMUxpy72W72yOaxysMLGgGMcHRnxOHUfj8qyqTnz5o5b8xa2CKi48nO4OT5sbp63zwj0JRmXOd18sz4%2F9G55Z%2FqXQKenp6SCOlpII4YYmhkccbQ1rGjgAAOAA8ScQjqfqHV9bS6nJKdbW2%2F5CGKGP1SQLTtf1zq7wXRtE%2F5vcnB1SW9MVO05cT4s4wPeI61kdT6tp7GGUNHEK261HrYKVh45%2FnP%2FAJrR%2FwCusjE2Gy1FC%2Boul1qBU3SudvVEo6GjqY3xNH%2FroCnDFdLH7jJv%2FtXL5%2Fsgf63pX%2BTLRRxwxtiiaGsY0Na0dAA6AlIQuW3btmhKgQhCgAQhCABCEIAEIQgAQhCABCEIAEIQgAQhCABCEIAEIQgAQhCABVY5e4zaNlYHXr2g%2BS9WnVWeXmM2vZSCenX9B8l6Zh90Ly%2BjO3%2BDubhziOlTmexA8SaqGbsjWbwcM9SeC2yv5MSr4I0L%2FmMfH60fEq28rx%2B9rDYkM9Gtqb9ZErExy%2FMWY%2Fmj4lXDlbO3tZbFM%2B7am%2FWRJkn4YqO6LkoQhco6yBCEIAEIQgAQhCABCEIAEIQgAQhCABCEIAwtfpiGasN2tNZNa7iemeDof9%2BzoclRXraBbgGVNrt13aOh8M3MSOHjId60H3lmELbDrsiio5EpJc%2F87i3iV2vBjDrDV%2BOGgHZ6v%2BVIv%2BCYlqde3nMc1RRWSnPT4Pmac%2BTePrR744rNIVvvlHzDHFP%2FAC%2F5bI7XLZjbPp%2B3WVr3UzHyVEvGaomdvyyHxlx%2BIcFkkIWPJknmlqm7YxRUVSBCEKhIIQhAAhCEACEIQAIQhAAhCEACEIQAIQhAAhCEACEIQAIQhAAhCEACq1y8279s2Us%2Fna%2FoB%2FsvVpVVzl2%2FyHZNn7YNu%2BJ6bh%2FcQvN6MsQ%2FT5ji59md4Y6VD3d31pzkcFtcjhJiFh6OJWrPwXvJ%2FnFdDIlfg5%2BPyYSH50z70fEq6crT6ctin47U36yJWLh%2BdM%2B9HxLjvKP2M6w2uQ6VqNEakoLLctM3M3KKoq2OcBIA3cLQGuBIc0HiMKGrVAvDLRIVNfQFy6%2FulrL2XF%2FDL30Acuw%2F%2FEtZey4v4ZY%2Ft5cmz7mJclC%2BfG2DVvLG2F2q0am1Xt%2ForjQ110ioHx0ttp2loIc4uJdAOGGHr61bv1TXJ5%2B3Ro%2FtaH95LnilEvDNGZ0xC5n6prk8%2Fbo0f2tD%2B8tz0nrPSeu7V5%2B6L1Hb73b%2BddD4VQztmi5xuN5u804yMjh5VRxa3GKSexlp54aaF9RUzMiiiaXve9wa1rRxJJPQEwbtbA1r3XCmDXEBpMrQCScAflJAHvqFqWpoKSK2T3SohhpGXm3OmfPIGRhoqYyN4kgYyB08D0Fb%2FeNd7P71aay0XTVenKqjrYJKeohkr4S2SN7S1zSN7oIJCdixKcbbE5Mrg6SNQnr6GkljgqayGKSUExsfIA54GMkA8TjIz74RDX0NRKaeCshkla3fLGyAuDc4zjxZ61H2cass1lvtXNfr7bqOeawWp8bq2pZG528%2BqLy3eI6S1mceIeRZrV%2BqdN6iulmFrvdpr6qA1A%2FuWpjlkZG5g3vYkkNJazPVkN8iOytGqw7z16aIiYmuFDTzNpp6yGOVzd8RueA4tzjOOnHlT690Fq%2BwWK9aoivGoLZRVBrKdkbauqjjfzIpo3NADiDu775D4sud5UvHDXKmXyz0RtDEFbR1T5IqaqilfFgvax4JbnOMgdGcH8yaju9pmYZYrnSvYCQXNlaRkHB456iCpmstSaf1BqCgms95tldPFbK1srqOoZI4N34N0O3SeGS7GfG7HSVsOm9ouiaPTtrpIdX6ehZDRQRtjFfC0MDWAAAb3DGMYTlgTk1Yp52op0a1DNDUwsqKeVksUjQ9j2ODmuaeIII4EJZIALnEADrWPsdXSV9PWVdDUQz08t0uLopIXhzHN8MmxukcCPeTWrZI4tKXqSV7WMZb6lznOOA0CN2ST1LO41Kh6lcdRIbfLM%2Bm8NbdqR1Pu7%2FOiZu5u%2BPezjHlT1RX0NKyJ9TWQRNncGRF8gaJHEEgNz0nAJ4eIrem7TNFsaGN1np5rWjAAuEOAP8AvLnuir3pix3ey11XcrVR0wtVU2ikmmjjjDd%2BnA5skgew4DHV5FoeBJpWIjnbTdD7Ljb5JmUzK2B0sgJZGJBvOA6cDpOFIUjaVrnTV3s9vp7Zqe0VdY28UJhZT1kcknrpmsfuta4k5je8HyEqOlZYdt0huLJ3FbGaitoqR8cdVVwwvmJEbXvDS8gZOAenASY7hQTTilhrYHzFpeI2yAuLQQC7HTjJHHyhStNahsGn9YVdRebtbaGU22JkD6udkZ3XSvLw0uI4Etjzj%2Ba3PQE7rrWGnb%2FXacprVqG1V1Q24SkspaqOR4j8FmJ4NJO7kNz1ZA8iusKcNVlHmanpox5uduEskJrqfnIjuyM5wZYcZwR1HBCdp6mnq4hPTTxyxkkB7HBwJBwRkeIgj8izWhNcaKsmkLVb26msFHIKcS1ERrIWO5%2BTL5XPBdnfdI57nZ4lxOeK0uTVWn23jXmpZL7QCz0VzbPNWNqGmnhjbbaR8ji4HdaAd5zvKST1onhUY2ghmcpU0Z5C5n6prk8%2Fbo0f2tD%2B8j1TXJ5%2B3Ro%2FtaH95J0y4Ha48nS3vbG10kjg1rQS5xOAB4yonnzaDB4SLnS8yW74k55u7u%2BPOcY8q0y2bYNju0EyWXTu0HTV9DHQSV1NBXRTNFIZ42SGQZxzZDt12eGHYK7jJtJ0TLE6GXWOnXxvaWOY6vhIIIwQRvdCdjw607E5M2hqjR6ito6Tm%2FCqqKHnnbke%2B8N33YJwM9JwCfeBXkdfQzTCnirIXyuaXiMPBcWjAJx04GR%2BcLBbINRWK1HTl1vt6t9PK7SVL4NPW1TWl3Oc2ZS1zz64u3IyTx6Ats2g6t0nqGOyRUWobPXVtPdY5KdtPVxvlbmORjyA12SNxzgf%2FwAKeytOqyO89emiOhCFnNAIQhAAhCEAN1NTTUcRnqp44YwQ0vkcGjJOAMnxkgflTbbjQPkZC2tgMkhIY0SDLiBnAHXwBP5Ehlys1q1dp%2BvvdbRUsNPJUyQy1crWMZNzLmgguIG9uveB14JWV2m670ld9IyUNNqmyVU7q6gdDHDWxOkLxVxEboDs595PhhUo6rETzOMtNGNkuNBDUGklrYGThgeY3SAODSSAcdOMg8fIUuCrpaov8GqYpebO6%2FceHbpxnBx0cCCntnestOWGu1bSXTUdqoqjz6jPN1NXHHJued9IRwc4HGS4jqySvdRXrTt%2B1dBcLJcrZXTutzoaqWjnZI4sZKDE15aTwBklLc%2FzneVDwpQ1WEczc9NCUIQkDwQhCABCEIAFVzl3ZNBsmAOP%2FaBb%2FkvVo1Vzl3fyHZN%2FnBt%2FyXpmH3QvL6MtOxjYaaR44%2BtyStVJBJJ8a2Kvn3KF0bT65wI%2FItbb0BbpO%2FLMK8GIhGYmfej4k4G%2BNJh%2Bcx%2Fej4ktTZWgQjB6UKCfBWHl808NXs80lS1DN%2BKbVVNG9uSMtMUoI4eRWFs%2FIp5LdqubdLak2O2maaXffba8zVLW1kY4mN%2BJMNnYOkDAe0b7QMPayv3Lx%2BkPR3420n6uVXv1i70TPdoe1keGPEdRU1jTxtbA7LJQQQRMSMxjxguOWjDrIhnEbnyKOSze7m%2FTendj9piFO5pudeypqHeDDgeYZ824TOac9B3GkOPEsB5b5m%2F9Tmfw%2FW%2FJiVxtITCy7uirixsVdTMfNDLk7twiLyXTtLnOc5%2B84GQOJcHuychzSac%2BZv8A1OZ%2FD9b8mJJ6j0HdP7lnLTp%2FVGphXVFLfrRSUsFW6njhntEk7sNa05c4VDAeJP1o6lN9LfVXun09%2Bj0n8Wszs5%2Bh1z%2FCc3yWLa1MMcXFeCJ5JKT8nPX7O9WyYMmqrA4gYG9p%2BU4HwtDNnerojvRaqsDD0Zbp%2BUf%2BLXQkK3ahwV7s%2BTjLrjenWGkkjnom3GouFNQOmdTPMIMlYyBz%2Bb5wO6HEhu%2F044lbG%2FZ3q2Q70mqrA4%2BN2n5Sf2tawz6E2z8YqH%2Feka7UlYoRd2NyzlGqfwc9Zs81dESYtV2FhPAlun5R%2FwCLSfS31V7p9Pfo9J%2FFroiE3tQ4Fd2fJyi92jVOlKyxMq75aKykuVwfRyxQWmSne0eCzzBzXGoeB66FoOWngT0dKVQW3UmpL5c7dbbxa6GloYac7tRbZKl8jpOc3skTxgDDBwx4%2BKzu0%2F59pL8Ov%2F3fWJvZ99M2o%2F8AIUH%2FAPslOEe5VDVOXbuyP6W%2BqvdPp79HpP4tKds81c8Na%2FVdhcG8ADp%2BU497%2B610JCb2ocCu7Pk543Zzqxjg5mqdPtI6CNPygj%2F5tapfL3fNO6Z1dXVM1DV1%2BnI6t0L46Z8UMpjpxKzejMjnDicHD%2BOOpduXB9o30r7UP8jcf2FqVlxxS8IbhnJvyzdn7PNXykGXVlheR0F1glP%2FAIteM2datjdvx6qsDXeMaflB%2Fa10NCb2ocCu7Pk52dnGqiSTqjT5J6c6ek%2Fi1zDlRac1bpbk6bQ7sdQ2SoijsNTHJBHZZYi9sjebdh3hLgDh3A7p6OhWTXFuWh9SxtK%2FAkny2qe1HgjuT5K58lzkr8n68bCdE672h7M7beItQ0Ilq7lPNMySkqDM5g5zdlAMTsNAIbljunLXZZ2HUXIx5K1HJBZbHsPslTea4EwMkqKvmoIwQHTykScGNzwHAvdhoIySJPJFu0dByQ9m1BFQMuNdcbTJBS0Djhs556QOLzghsTQcvcQcDgA5xa09N0vbnbOK4W%2B%2B1Iqobw%2BOOG7yZBZNkiOieXElsYzuwcTnJa4mQ70tyhTbY3sbsuz7l6a62b7N3Ulot1u0dSyAVVK%2Bqa8vbRvkO6JGYLnvLuBwOgDGMXD9LfVXun09%2Bj0n8Wq%2F6A%2F%2FAFOdpv4lUf6uhVxFRwjLy0XU5R8JnPHbO9WvDQ%2FVVgcGjDQdPynA8n91obs61YxwczVNga4dBGn5Qf2tdDQo7UOCe7Pk5ZbXXDcqKe5z089RS1U9M6WCF0THhjy0EMc95HAD64qWmIP5ZdPwnV%2FrXJ9YJqpNI3wdxTYIQhVLAhCEANWvSWrb9bYbl6JbHFHUbz2RSWSWQsbvEAF3hQycdeB7wUkbONVtIcNUafBHEEaek%2Fi1tOifpWt3%2BSPyis2ugsUK2Oe8kr3OeP2datkcXyaqsDnHpJ0%2FKT%2B1pit0NrC2UFVWwapsAdDC%2BTdFglG9ugnBxVrpSg376B3H%2ByTfIKntQ4I7s%2BTQoXmSGOR2MuaCcdGSEtN0v8mh%2FwAm34k4ucdFbAhCEACEIQAKrfLwOLdsnPi2gW8%2F7L1aRVa5eY3rZspb49f0A%2F2XpmH3RTL6MsdUV0RBy4uJBHDqWNYctzhT46JgGXqFOAyVzRwAPBbn5MCMTD85j%2B9HxJwcB0JEA%2BZRgcfWj4lJho5p3AMjKkrQznPBP09DPUHDGHHjWYp7JHBFz05yf5qc58MZuRgNHkRtuSio%2Fmh1LNa9l2lauBvOzs1TTuZG0ZLnCGYgY68kLfrFyzdr1goTR03Ij2jyvkkdPUVE9TM%2BWomd7KR7jTdJ4AAcGgNa0BrQBp3mhW8dnGjifddS8f8A%2BqVXBScmV46odjxLI3ZW%2B%2B8s7bBfaRsEvIl2jU88EjZ6WqhmkEtPK3oe0mlI8YIOQ5pc0ggkFfIJ0dqzQ2wg2PWWnLjZLh59Vc3gtfTPgl5tzY91264A4ODx8isahIyZnkVD8eFY3aF6Y1PFp2K4UVbZ7lI6SuknY%2BCJr2OY5rcEHeHiKzXpjW72jvXwZv7y06vZqJtkZfqG526OOS90lsEEtA%2BRwZLWx0xdviZuSOcLx63HDHlW0ege%2FwDukt%2FZb%2B%2FT4vLSpCJLFqdsk%2BmNbvaO9fBm%2FvI9Ma3e0d6%2BDN%2FeUb0D3%2F3SW%2Fst%2Ffo9A9%2F90lv7Lf36m8vBFYuWaXzNayx0UvnfOZIbtSVz4AG84I2V7JncM4yGAnGerC330xrd7R3r4M395RvQPf8A3SW%2Fst%2Ffo9A9%2FwDdJb%2By39%2Bqwjkjsi0pYp1ZJ9Ma3e0d6%2BDN%2FeR6Y1u9o718Gb%2B8o3oHv%2Fukt%2FZb%2B%2FR6B7%2F7pLf2W%2Fv1a8vBWsXLMNq3UA1NWadioLPco20V0fVTyTxNYxkfgdTHn2Rz6%2BRgwB1rzT97Gm9Q3iorLVXzw1sNIIpKeNrwSznd4H1wII3h%2BdZr0D3%2FAN0lv7Lf36PQPf8A3SW%2Fst%2FfqunI5ai2rFp0kn0xrd7R3r4M395HpjW72jvXwZv7yjege%2F8Aukt%2FZb%2B%2FR6B7%2FwC6S39lv79WvLwVrFyyT6Y1u9o718Gb%2B8ucaqorjqDS%2Buo6G3TsnvcVaKOGXda95fSiNoPHAy4EcSt%2B9A9%2F90lv7Lf36PQPf%2FdJb%2By39%2BqyWSfhloSxQdok%2BmNbvaO9fBm%2FvI9Ma3e0d6%2BDN%2FeUb0D3%2FwB0lv7Lf36PQPf%2FAHSW%2Fst%2Ffq15eCtYuWSfTGt3tHevgzf3ly7lQ3is13yfdd6R0zpa%2BVd0udokipYG0wLpHgh26AHEk4BwAMk8Aukege%2F%2B6S39lv79HoHv%2Fukt%2FZb%2B%2FReXgKxcspByfuUFtk2IbN7Jomp5HW0K%2BV9qpPA3XFwkizEHueI42eDHcYC7OMkkkkk8MdFuHLa2uXSimt1w5DGv56aoYY5Y3Sy4c09X8m%2F1qzXoHv8A7pLf2W%2Fv0ege%2FwDukt%2FZb%2B%2FU3k4IrHyUx5MV52n3Tla6t2x7QdkmsdO2666ZZbabzwo5HyfMnUzGNfM9rBI%2FciJLsAnBJHSrr%2BmNbvaO9fBm%2FvKN6B7%2FAO6S39lv79HoHv8A7pLf2W%2Fv0Xl4CsXJJ9Ma3e0d6%2BDN%2FeR6Y1u9o718Gb%2B8o3oHv%2Fukt%2FZb%2B%2FR6B7%2F7pLf2W%2Fv1F5eCaxcswNBI%2Bc1lW%2BnlgFTW1E7GSgB4Y6RxbkAnHBSllPQPf%2FdJb%2By39%2Bj0D3%2F3SW%2Fst%2FfpDwTk7HrPCKoxaFlPQPf%2FAHSW%2Fst%2Ffo9A9%2F8AdJb%2By39%2Bo%2B3mT9xAxaFlPQPf%2FdJb%2By39%2Bj0D3%2F3SW%2Fst%2Ffo%2B3mH3ECPp7WlNaLNTWyrsl2MtOHMcWQNc0nePEHe4hZH0xrd7R3r4M395RvQPf%2FdJb%2By39%2Bj0D3%2F3SW%2Fst%2FfrQnlXwZ32m7tkn0xrd7R3r4M395Rbrr6iq7XWUsNivJkmp5I2A07RlxaQPr1jr7pzUtqjonQ6gtjzVV0FKd61yYa178E%2FyjpxnCYYyspq64WyuqIZ5KGobEJYYTEHtdFHIPWlzsEb%2BOnqzwVZzyQVtFoQxzdJiqdpZBG1wwWsAI8uEtCFjNgIQhAAhCEACq3y8Podsn%2FzgW%2F5L1aRVa5eX0N2Ufj%2FAG%2F5L0zD7oXl9GWTc5oHSsZVszOSD08VJc4k9KakGXfkXQaOcmM0NpdG2N0hyN0fEs24tpmesiAPvJmIuEUZ6Tuj4kuUySdWSqJjHewxLUPkGHFIZGXEHHBOtpZCd535k%2B2Fw4YAVJSbLRRVDzQ4AbNdGj%2FpfS%2FqplbxV75Y2xjXm2XQVjsmz1lDLcrbfIrk8Vc4iYGMikHTg5O85vD31qJqvNHR00Ozf8%2F%2FAJkqcHNKhuOag3ZbJCqYa3zRwDJotm%2F5%2FwDzJPnh5o0P%2BZbN%2FwA%2F%2FmVOxIb3olvKOz1990P4FbOY8Ihv8Fc0TyFjHCnuMc7mlwa4glsRA4HiQtr8K1X7R2vtOTuFQWe3%2BaDT1ElT4FoKF8zt%2BTweumga92AN4tjlAJwBxxngkedXmg5%2Fwei%2B16rvlri2kkY5JNtl%2FfCtV%2B0dr7Tk7hHhWq%2FaO19pydwqDecfmhe7viLRePwvVd8kedXmg%2FXHosf9r1XfK2p8EaUX88K1X7R2vtOTuEeFar9o7X2nJ3CoEbX5oOOlmi%2B16rvkC1eaDE4DNF9r1XfKNTDSX98K1X7R2vtOTuEeFar9o7X2nJ3CoKLL5oURkRaL7Xqu%2BXvnJ5oX9i0X2vVd8o1hoL8%2BFar9o7X2nJ3CPCtV%2B0dr7Tk7hUG85PNC%2FsWi%2B16rvl55y%2BaFj%2FBaL7Xqu%2BRrJ0F%2BvCtV%2B0dr7Tk7hHhWq%2FaO19pydwqC%2Bcvmhf2HRna1V3yPOXzQsf4LRfa9V3yNYaC%2FXhWq%2FaO19pydwjwrVftHa%2B05O4VBfOXzQv7Dozteq75HnL5oX1w6L7Xqu%2BRrDQX68K1X7R2vtOTuEeFar9o7X2nJ3CoJ5zeaFfYdGdrVXfI85%2FNC%2FsGje1qrvkaw0F%2B%2FCtV%2B0dr7Tk7hHhWq%2FaO19pydwqB%2BdHmhf9H0b2tVd8jzp80L%2Fo%2Bju1qrvkaw0F%2FPCtV%2B0dr7Tk7hHhWq%2FaO19pydwqBG1eaFN%2F5vo7taq75J87fNCR%2FzbR%2FatV3ynWGgv%2F4Vqv2jtfacncI8K1X7R2vtOTuFQEW3zQg9MOjR792qu%2BT0Ni80Nn%2Bdw6L%2FAC3eq75GuyNBfjwrVftHa%2B05O4R4Vqv2jtfacncL5%2Bz0nmgdPIY5I9G7zenF2qu%2BSGw%2BaAO6I9Hdq1XfKdT4I0rk%2BgvhWq%2FaO19pydwjwrVftHa%2B05O4Xz78H80Bz860d2rVd8vDD5oA04Meju1arvlGqydKPoL4Vqv2jtfacncI8K1X7R2vtOTuF8%2BxT%2BaBHoi0d2rVd8nbdbvNBrpW1FBSw6N52liimk3rtVAbsheG4%2BbeON3%2BpGphpRf%2FAMK1X7R2vtOTuEeFar9o7X2nJ3CoVT6d80PqZqmGKHRW9SSiGTN3qh64sa%2Fh8248HtUSO3%2BaCyWE6kbFo3wMU7qknz2qt7cAJPDnunARqZGlcn0A8K1X7R2vtOTuEeFar9o7X2nJ3CoVV6b80QoomzTw6K3XSxxDF3qj6572sb%2FhvG4JuWyeaFxXWnsz4tF%2BE1VPNVRgXeq3dyJ0TX5PPcDmZn%2BvxI1MNK5L2XKi1LeJKCKot1tpoaauhqpHsrnyO3WOzgNMTck%2B%2BFrlf9M1%2FwD7bF%2BywKnUenfND5ayagZDovnYGMkf%2FwAr1WMP3g3%2FAA39QqRYo%2FNEvAah9rt2zkRQ1M8UzpJHPe%2BWN5Y8lznlzuLcAk9ACXlTnGkMxSWOVst2hVKhuHmjU1nhvrKHZv4LPAyoYSfXbjwCOG904ISq%2Bt80ct1VbqOpotm4kulS6kp905BkEMkxz67gNyF%2FHx48az9iRp%2B4gWzQql1dw80WoJ3QV0WzGnDIDUPkllDI2sDg3JcXYHEhYOHaXy4JZZ4fRVsWYYHhhc%2B6Qta%2FLQ7LTznrh67GR1gjqT8X07qcy1Y4tr8FX1WNbl0EKnVo1jy%2Fb%2FQVNxs1ZsprI6Rsr5mQVLJJGtjJBJa15ODjIPWCD1q31Aap1BTOrtzwkwsM257HfwN7HkzlJz9Nk6Z6cqpjMeWOX1H1Vrl5fQ3ZR%2BP9v%2BS9WlVWuXl9DdlH4%2F2%2F5L1XD%2B4gy%2BjLEnyBebmeJTgAPFe48i6dWcuyXDH8xYf6o%2BJOtG6iEZhj4fWj4ko4HUsVs2ngd4vzpQYOlxKBjpwvS7gosKs93mt9iEzI8njleySYCZLyUbk0hRcUgnKM8F5jJUojcSfIlMGXBeHA6Alxt9dxVo%2BWUfgkyvDKf31iznOckqfWH5k0BQU74FWeb3kXnHPSvT0r1rHOOAo8FvLJNI5xGCpfDxFNwRc23j0p5JluNiqQk4Xi9dhIIyVUsBOUAFehvDJXhOeAQAcB0Lzp45K9wUoAjpQAkNyjGOH%2FANEpBGOlACN3PErwnAwEou8SSQVAI8PFecRxylYITckjW5A6VZWwGHvL5iwdSzFuYI4HynhgLDUkZkkJx0lZiseKO3OHW4YCvjj5sXkZq9bJztU9%2FwDWTUfRwSXkucSevKW0YCZPYVHcOIOcJcbWgZceJ4r2NocePUE0TkkqtPT4Lxa1LVsc%2FwBmO3DTO0WFlCC23XkN9fRSu%2BeHGSYnfXDp4dI8XWuo6IJOp73n%2BgUH6yqXDdk3J6tOjX0%2BoNUOjuN6jIkiYOMFK7HUD7Nw%2FnHgDjAyMrtGlbVa7lqi8G422lqjHQUAZz8LX7uZKrOMjh0BYfpr6uWC%2BsSUv%2FX5O%2F8A1ND6Tj65x%2Bjybx%2Fna%2Fmvlr%2B5tlm%2BiN9%2FCDP2WBa7Sf3n3%2FgaX9W5T7PpjTTrhfA7T1sIbXsAzSR8B4LAfEtepdN6d9KR83nDbuc855Xb3grM55t3HOFuPOm66j%2Bh8X9vof2qJQa7%2B%2BHZPwLdP19CmtQ6Y002giLdPWwHw6iGRSR9dTGD1KFXaZ02Nf2WMaftoY6z3Nxb4JHgkT0ODjHlP5ygDPUf0z3T%2ByUnyplj9I%2FQa6%2Fha6ftMqRSaY02dS3Nnoetm6KWkIHgkeB66byKBpPTOnH2e6Ofp%2B2uIutzaCaSM4AqZMDoQBMt397K2fgmk%2FVsTurvpg0T%2BHZf92VyxFv01pw7NbbKdP20vNqpSXGljyTzbOOcJ3VmmtOMv2i2s0%2FbWh98la4CkjG8PO2tODw48QD%2BRAGE19ZbdqzbXs30dfoPCbRcnV0tXSlxDJjBTvljDgOlu80ZB4HoT3KTt%2BzvZBpq33vTezzZf4ZUVBh87rpp1kktW3Ay6ExbuNzpdvjdIcPXB261%2BO1fFatL7ctnNzoqC2UbNy5w5kcKWGSeSklZCySRrXFoc9wbvbriN7gD0LcafYSdU65rNXbT6SjuzoBHBAJBzkNSxj%2BcjEcTiRBCC4ZYMPcW824ua2aav9n02bD00ejy9S32owbcV%2Fueudr%2FACqt71X91zOpWSanDEv1N78eEcBhvtDqHQGn9pdu0dp3TN4brT0NSCwUZo4Z6KWjL3iRgcQ52T0nq%2FLmxkXzpn3oWlcpiyactNr07ZLTRUcFfe9ZQ3yspYsA1BbSyxz1BZnAyBHvEAAuJJy5zid1i%2BdM%2B9C5H9S9RDqseLPCLjFuVJu6japW90ndHQ%2BlY5YlKE3bVWxSq1y8fobso%2FH%2B3%2FJerSqrXLx%2Bhuyj8f7f8l68xh%2FcR0s3oyx4AC9LM9CTvIDyOtdXY5O5koOEMf3o%2BJJk4FKh4xM%2B9HxLyVc86B409GV6SkA46V4TlQFnjwHHpSSwDglpLipATgBBAC9SXkNaSSp3KsSXBoykxTF8uB0KPLNnrSqRr3yAN606CoVNk6s4tbx6AoOOv8yys1NiJo68KNHQk8X8FaTRWKZGiifI7AU%2BKBkfQMlOMiawYaMJRPUElysdGNHi8JXq8wFQuJ4lKwBxK8Lh0BecSgAOSehegZ4r0DC9QB5jCMY6V6kkoAC4AcEgnK9zngvCMIA8R5SgkNGSVHlm3sgdCtGNsiUq8CpZ8cGH8qYzvcEnPUpFNAXvHDjlXfjwKTvyTLdT%2BuDyMBqjahqDhsQPUsqxvMxbo6SFr92dz0sjgfYHdCvFUVlK2YwY6V63oSQCEpqJBAdYd1jimRxKecMQ58aZPAqUVluKXI9rehdvGoNSU952P7Y4tG0pomU1bA6jbMZ5GPkc1%2BS04wJCF1kuSDvP4AIsgrbHsp5aVPJNJDyqoGvqHiSUi1M9c4Na3PsP5rWj8iYj2M8srzrNkZyoqcURiMBh86243CMFvsM9BVm2Qdbk80BvQFZILK1T7IeWtVRiOo5VcD2teyQA2pnBzHBzT7DqIB%2FIku2N8tGSuhuT%2BVTTmpgikgjk86mZbHIWOe32HWY2H%2FqqzOSjJyikRbK0s2PctRlTJVM5VlOJZWtY93nUzJDc7o9h1bx%2FOkU2xfloUUUkFNyqaeNk0skz2i1N4vkcXPPsOskn8qs408V64nOVKSItlYWbFeWbDbY7UzlUU4pIomwsi86m4DGgAD2GeAAXsuxfln3GqopqjlU075aGc1FM42pnzOQxvjLh6z%2BZI8f9ZWcJy1Ko%2FwCUx%2B%2BhJXQW6Ku6l5OfK7vTRT6h5TVsuUb2bu5UWSN7QM56Czpz19K1X1G3KA%2B3fp7sGP8AcV2rsSZmY6goYz1LoYPqXWdFHt9PllFcJtC3CE%2FMlZT20cl3lM6dnklsu36yUc0sb4nSw2KJr9xww4B3N5GQcHC2OLY1y0XesHKqpxge1TP3FZWfhME9T%2FPCsPU9Vm63Jr6iTk%2BW7%2FkfCKxqoeCtA2LctM%2F%2FABWU%2FZTf3FibxyW%2BUfrW7adn2k8oGh1Bb7Bd6e7R0slu3PXxuBO6WgcS3eHHhxVtC%2FCQ6TrCooRXlIhzk%2FDYt0gaOlRnVYDiGtJHjCHb7zut609HSta3BCt5ZXwZqEfMGD%2BqPiXkrDhLp%2FnLD%2FVHxJ14G70LCbqIOMox4k4QMlIB6SpIEuOOtNl3WTjC9lOOATHsuklABLVMYOByUxvVFQcNacKdDSQE5LclTI4o2HDWhWTorRjYrc8YLhklT6WmDDndT54DISoz65SptkOKQ4W8AUycdafdwamHDj%2BVTLYheBGV4vcAnCDwHBLGHh4DpSHOJ4BeuPFAGUAeNac5SgMIxhejr95AAB5V7wA6l4kk5QAE5XiEIYI8I8SbkmZH7IjPiSqhxY31qx73EniVaMbKylQ4%2BZ8nEngm8knA4lJz1KZTxtDN%2FHHCckkJtsTHT49lxcf9SylLE2Ju%2BRxUekaHHJGVJeTnHUoW9hJ0qPJpd0GR3QAtfkeX0j5j0yPystc3uZRylpwVipgBbY8dfFXXkqyDjh%2BRAPFeZ4JTOLgPKqS3LR2FznDWtTOcpdRxlIKS0BSirYkNLinGM3epKaAOK9PiVkiGwQvAcr1WsqC9A8a9b0JTelQQetbkoI6kpnskPGHKUAl3sSlUP8qj99If7FOUPGqZ76mPsT8Eu5cZm%2FeqLkAKTcPnw%2B9UVTJ0yERanhOE5DwkPvJuo4vafInIvZn3kj5HLYdJ3uhJ3S7gF6D1JxgTEKYRxhvEhPjIHALxoCWmIo2f%2F9k%3D;imageWidth=600;imageHeight=640;\" parent=\"0\"/>\r\n" +
                "    <mxCell id=\"3\" style=\"ts2\" parent=\"1\" vertex=\"1\">\r\n" +
                "      <mxGeometry x=\"170\" y=\"120\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"4\" value=\"1\" style=\"label\" parent=\"3\" vertex=\"1\">\r\n" +
                "      <mxGeometry x=\"0.1\" y=\"0.1\" width=\"30\" height=\"10\" relative=\"1\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"6\" style=\"ts2\" parent=\"1\" vertex=\"1\">\r\n" +
                "      <mxGeometry x=\"190\" y=\"30\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"7\" value=\"2\" style=\"label\" parent=\"6\" vertex=\"1\">\r\n" +
                "      <mxGeometry x=\"0.1\" y=\"0.1\" width=\"30\" height=\"10\" relative=\"1\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"9\" style=\"ts2\" parent=\"1\" vertex=\"1\">\r\n" +
                "      <mxGeometry x=\"80\" y=\"190\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"10\" value=\"3\" style=\"label\" parent=\"9\" vertex=\"1\">\r\n" +
                "      <mxGeometry x=\"0.1\" y=\"0.1\" width=\"30\" height=\"10\" relative=\"1\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"12\" style=\"ts2\" parent=\"1\" vertex=\"1\">\r\n" +
                "      <mxGeometry x=\"70\" y=\"60\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"13\" value=\"4\" style=\"label\" parent=\"12\" vertex=\"1\">\r\n" +
                "      <mxGeometry x=\"0.1\" y=\"0.1\" width=\"30\" height=\"10\" relative=\"1\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"14\" value=\"2층\" style=\"tblGrpFg=1;\" parent=\"0\" visible=\"0\"/>\r\n" +
                "    <mxCell id=\"16\" style=\"ts2\" parent=\"14\" vertex=\"1\">\r\n" +
                "      <mxGeometry x=\"110\" y=\"220\" width=\"110\" height=\"70\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"17\" value=\"1\" style=\"label\" parent=\"16\" vertex=\"1\">\r\n" +
                "      <mxGeometry x=\"0.1\" y=\"0.1\" width=\"30\" height=\"10\" relative=\"1\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"19\" style=\"ts2\" parent=\"14\" vertex=\"1\">\r\n" +
                "      <mxGeometry x=\"230\" y=\"80\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"20\" value=\"2\" style=\"label\" parent=\"19\" vertex=\"1\">\r\n" +
                "      <mxGeometry x=\"0.1\" y=\"0.1\" width=\"30\" height=\"10\" relative=\"1\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"22\" style=\"ts2\" parent=\"14\" vertex=\"1\">\r\n" +
                "      <mxGeometry x=\"250\" y=\"170\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"23\" value=\"3\" style=\"label\" parent=\"22\" vertex=\"1\">\r\n" +
                "      <mxGeometry x=\"0.1\" y=\"0.1\" width=\"30\" height=\"10\" relative=\"1\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "  </root>\r\n" +
                "</mxGraphModel>\r\n" +
                "";
        graph = new mxGraph();
        Document doc = mxXmlUtils.parseXml(xml);
        mxCodec codec = new mxCodec(doc);

        model = graph.getModel();
        Element elt = doc.getDocumentElement();

        codec.decode(elt, model);
    }

    /**
     * mxGraph XML 파싱 테스트
     *
     */
    @Test
    //@Ignore
    //@Rollback(false)
    public void test_100() {

        try {
            //TODO 테이블 그룹
            /*
            List<mxCell> cells = new ArrayList<mxCell>();
            while(elt != null) {
                cells.add(e)
            }
            */
            List<TableGroupVO> tableGroupVOs = new ArrayList<TableGroupVO>();
            TableGroupVO tableGroupVO = new TableGroupVO();

            List<TableVO> tableVOs = new ArrayList<TableVO>();

            mxCell layer = new mxCell();
            for(int i = 0; i < model.getChildCount(model.getRoot()); i++) {
                layer = (mxCell)model.getChildAt(model.getRoot(), i);
                LOGGER.debug(layer.toString());

                tableGroupVO = new TableGroupVO();

                //TODO 테이블 그룹 파라미터 생성
                tableGroupVO.setStoreCd("S000001");
                //tableGroup.setTblGrpCd("");
                tableGroupVO.setTblGrpNm(String.valueOf(layer.getValue()));
                //TODO 배경이미지 그룹별로 넣을 수 있게 JS부터 개발할 것
                //tableGroup.setBgImgNm("");

                //스타일
                String styleStr = layer.getStyle();
                LOGGER.debug(styleStr);
                if(styleStr != null) {
                    String[] styles = styleStr.split(";");
                    for(String style : styles) {
                        LOGGER.debug(style);
                        String[] styleKeyValue = style.split("=");
                        if(styleKeyValue.length < 2) {
                            continue;
                        }
                        LOGGER.debug(styleKeyValue[0]);
                        LOGGER.debug(styleKeyValue[1]);
                        switch(TouchKeyStyle.getEnum(styleKeyValue[0])) {
                            case TBL_GRP_FG:
                                tableGroupVO.setTblGrpFg(TblGrpFg.getEnum(styleKeyValue[1]));
                                break;
                            case BG_COLOR:
                                tableGroupVO.setBgColor(styleKeyValue[1]);
                                break;
                            case BG_IMG:
                                tableGroupVO.setBgImgNm(styleKeyValue[1]);
                                break;
                            default:
                                break;
                        }
                    }
                }
                tableGroupVO.setDispSeq(Long.parseLong((String.valueOf(i+1))));
                tableGroupVO.setUseYn("Y");
                tableGroupVO.setRegDt(currentDateTimeString());
                tableGroupVO.setRegId("bjcho");

                tableVOs = getTables(layer, tableGroupVO);

                tableGroupVO.setTables(tableVOs);

                tableGroupVOs.add(tableGroupVO);

                LOGGER.debug(tableGroupVOs.toString());
            }
            assertTrue(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 레이어 id로 해당 레이어에 있는 테이블 List 추출
     * @param id
     * @return
     */
    private List<TableVO> getTables(mxCell layer, TableGroupVO tableGroupVO) {
        List<TableVO> tableVOs = new ArrayList<TableVO>();
        TableVO tableVO = null;
        Object[] cells = graph.getChildVertices(layer);
        for(Object c : cells) {
            mxCell cell = (mxCell) c;
            tableVO = new TableVO();
            tableVO.setStoreCd(tableGroupVO.getStoreCd());
            tableVO.setTblNm(String.valueOf(cell.getValue()));
            tableVO.setTblGrpCd(tableGroupVO.getTblGrpCd());

            //스타일
            String styleStr = cell.getStyle();
            if(styleStr != null) {
                String[] styles = styleStr.split(";");
                for(String style : styles) {

                    String[] styleKeyValue = style.split("=");
                    if(styleKeyValue.length < 2) {
                        continue;
                    }
                    switch(TouchKeyStyle.getEnum(styleKeyValue[0])) {
                        case TBL_SEAT_CNT: tableVO.setTblSeatCnt(Long.parseLong(styleKeyValue[1]));
                            break;
                        case TBL_TYPE_FG: tableVO.setTblTypeFg(TblTypeFg.getEnum(styleKeyValue[1]));
                            break;
                        default:
                            break;
                    }
                }
            }
            //좌표, 크기
            mxGeometry geo = cell.getGeometry();
            tableVO.setX((long)geo.getX());
            tableVO.setY((long)geo.getY());
            tableVO.setWidth((long)geo.getWidth());
            tableVO.setHeight((long)geo.getHeight());

            tableVO.setUseYn(tableGroupVO.getUseYn());
            tableVO.setRegDt(tableGroupVO.getRegDt());
            tableVO.setRegId(tableGroupVO.getRegId());

            tableVOs.add(tableVO);
        }
        return tableVOs;
    }

}
