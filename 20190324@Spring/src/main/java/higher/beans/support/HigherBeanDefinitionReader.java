package higher.beans.support;

import higher.beans.config.HigherBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HigherBeanDefinitionReader {
    private String[] configLocations;
    private Properties config = new Properties();
    private List<String> registryBeanClasses;

    /**
     * 固定配置文件的key
     */
    private final String SCAN_PACKAGE = "scanPackage";

    public HigherBeanDefinitionReader(String[] configLocations) {
        doLoadConfig();
        doScanner(this.config.getProperty(SCAN_PACKAGE));
    }

    private void doScanner(String scanPackge) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackge.replaceAll("\\." , "/"));

        File classPath = new File(url.getFile());
        for(File file : classPath.listFiles()) {
            if(file.isDirectory()) {
                doScanner(scanPackge + "." + file.getName());
            } else if(file.isFile()) {
                if(!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = (scanPackge + "." + file.getName().replace(".class" , ""));
                this.registryBeanClasses.add(className);
            }
        }
    }

    private void doLoadConfig() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(configLocations[0].replace("classpath:" , ""));
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<HigherBeanDefinition> loadBeanDefinitions() {
        List<HigherBeanDefinition> result = new ArrayList<HigherBeanDefinition>();

        for(String className : registryBeanClasses) {
            HigherBeanDefinition bd = doCreateBeanDeinition(className);
            if(null == bd) {
                continue;
            }
            result.add(bd);
        }

        return result;
    }

    private HigherBeanDefinition doCreateBeanDeinition(String className) {
        try {
            Class<?> beanClass = Class.forName(className);
            if(beanClass.isInterface()) {
                return null;
            }

            HigherBeanDefinition beanDefinition = new HigherBeanDefinition();
            beanDefinition.setBeanClassName(className);
            beanDefinition.setFactoryBeanName(toLowerFirstCase(beanClass.getSimpleName()));

            return beanDefinition;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Properties getConfig() {
        return config;
    }

    private String toLowerFirstCase(String name) {
        char[] chars = name.toCharArray();
        chars[0] |= (1 << 5);
        return String.valueOf(chars);
    }
}
