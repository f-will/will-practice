package com.will.framework.util.tool;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * zookeeper 
 *
 * @author: will
 * 
 */
public class ZkPropertiesFactoryBean extends AbstractFactoryBean<Properties> implements Watcher,ApplicationContextAware {
	Log log=LogFactory.getLog(ZkPropertiesFactoryBean.class);
	private String zkConnect; //zookeeper链接地址
    private String path;	 //数据节点地址
    private int timeout = 60*1000;  //超时时间
    private ZooKeeper zk;
    
    //Watcher 是一次性的服务端通知一次Watcher就会失效,所以在做完操作后需要重新注册
	@Override
	public void process(WatchedEvent event) {
		if(event.getType()==Event.EventType.NodeDataChanged){  
			log.info(path+"dataChanged");
			try {
				//更新spring 上下文 此处可以刷新spring 上下文  这里有问题暂时不要使用
//				ConfigurableApplicationContext cac =(ConfigurableApplicationContext) applicationContext;
//				cac.refresh();
				//重新注册监听
//				loadFromZk();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	protected Properties createInstance()  {
		long start = System.currentTimeMillis();
        Properties p = new Properties();
        try {
			p.load(new ByteArrayInputStream(loadFromZk()));
		} catch (IOException | KeeperException | InterruptedException e) {
			log.info(String.format("Loaded %d properties from %s:%s fail", p.size(), zkConnect, path));
			e.printStackTrace();
		}
        double duration = (System.currentTimeMillis() - start)/1000d;
        log.info(String.format("Loaded %d properties from %s:%s in %2.3f sec", p.size(), zkConnect, path, duration));
        return p;
	}

	@Override
	public Class<?> getObjectType() {
		return Properties.class;
	}
	
	private byte[] loadFromZk() throws IOException, KeeperException, InterruptedException {
		if(null==zk){
			 zk = new ZooKeeper(zkConnect, timeout, this);
		}
	    byte[] data = zk.getData(path, true, null);
	    //zk.close(); 暂时注释
	    return data;
	}
	
	public void setZkConnect(String zkConnect) {
		this.zkConnect = zkConnect;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		log.info("----------   load spring  ----------");
	}
	
}
