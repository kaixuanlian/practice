import com.liankaixuan.annotation.CustomizeTransactional;
import com.liankaixuan.domain.MyBean;
import com.liankaixuan.factory.ProxyFactory;
import com.liankaixuan.interceptor.MyTransactionInterceptor;
import com.liankaixuan.service.TransferMoneyService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;

/**
 * @auther liankaixuan
 * @date 2020/5/9 8:51 下午
 */
public class SpringTest {

	@Test
	public void testIoC(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//		applicationContext.getBeanFactory()
		MyBean myBean = applicationContext.getBean(MyBean.class);
		TransferMoneyService transferService = (TransferMoneyService) applicationContext.getBean("123");
		System.out.println(transferService);
		System.out.println(myBean.getMyBeanTest());
	}

	@Test
	public void testTransaction(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		TransferMoneyService moneyService = (TransferMoneyService) getBean(applicationContext, "123");
		moneyService.transfer();
	}

	public Object getBean(ApplicationContext applicationContext, String beanName){
		Object bean = applicationContext.getBean(beanName);
		if (!hasAnno(bean, CustomizeTransactional.class)){
			return bean;
		}
		ProxyFactory proxyFactory = ProxyFactory.instance;
		return proxyFactory.getProxy(bean);
	}

	private boolean hasAnno(Object bean, Class clazz) {
		if (bean.getClass().getAnnotation(clazz) != null){
			return true;
		}
		for (Method method : bean.getClass().getMethods()) {
			if (method.getAnnotation(clazz) != null){
				return true;
			}
		}
		return false;
	}
}
