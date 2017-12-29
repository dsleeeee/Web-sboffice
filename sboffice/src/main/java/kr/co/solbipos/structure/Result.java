package kr.co.solbipos.structure;

/**
 * 전달 결과
 * @author 이호원
 */
public class Result
{
    /** 응답 유형 */
    Status status;
    
    /** 데이터 ( 필요시 전달 ) */
    Object data;
    
    protected Result(){}
    
    public Result( Status status )
    {
        this.status = status;
    }
    
    public Result( Status status, Object data )
    {
        this.status = status;
        this.data = data;
    }
    
    public Status getStatus()
    {
        return status;
    }
    
    public Result setStatus( Status status )
    {
        this.status = status;
        return this;
    }
    public Object getData()
    {
        return data;
    }
    
    public Result setData( Object data )
    {
        this.data = data;
        return this;
    }
    
    public enum Status
    {
        OK( "OK" )
        , LOGIN_BLOCK( "error.loginBlock.default" )
        , LOGIN_EXFIRE( "error.sessionExpire.default" )
        , LOGIN_FAIL( "error.loginFail.default" )
        , PASSWORD_EXPIRE( "error.expirePassword.default" )
        , PASSWORD_REUSED( "" )
        , PASSWORD_WRONG( "error.wrongPassword.default" )
        , DUPLICATE_DATA( "error.exist.default" )
        , NOT_SELECTED( "error.notExist.default" )
        , NOT_INSERTED( "error.notInserted.default" )
        , NOT_UPDATED( "error.notUpdated.default" )
        , NOT_DELETED( "error.notDeleted.default" )
        , NOT_AUTHENTICATION("error.access.denied")
        , VALID_FAIL( "error.validFail.default" );
        /** 응답 결과 ( message code ) */
        String reason;
        
        Status( String reason )
        {
            this.reason = reason;
        }
        
        public String getReason()
        {
            return reason;
        }
    }
}