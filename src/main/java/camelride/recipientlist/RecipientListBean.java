package camelride.recipientlist;

import org.apache.camel.RecipientList;
import org.apache.camel.language.XPath;

/**
 * Author: sazal
 * Date: 5/7/17.
 */
public class RecipientListBean {

    @RecipientList
    public String[] route(@XPath("/order/@customer") String customer){
        if(isPremiumCustomer(customer)){
            return new String[]{"jms:queue:accounting", "jms:queue:production"};
        }
        return new String[]{"jms:queue:accounting"};
    }

    private boolean isPremiumCustomer(String customer){
        return customer.equals("honda");
    }
}
