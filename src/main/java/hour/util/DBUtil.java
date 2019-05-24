package hour.util;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

public class DBUtil {
    public static void rollBack(){
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
