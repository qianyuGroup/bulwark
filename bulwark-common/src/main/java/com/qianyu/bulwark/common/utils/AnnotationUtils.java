package com.qianyu.bulwark.common.utils;

import com.qianyu.bulwark.common.annotation.RpcReference;
import com.qianyu.bulwark.common.annotation.RpcService;
import com.qianyu.bulwark.common.model.RpcServiceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

/**
 * 注解工具类
 *
 * @author junlong.njl
 * 创建时间 2023-02-19
 */
public class AnnotationUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(AnnotationUtils.class);

    public static final String CLASS_SUFFIX = ".class";
    public static final String JAR_PROTOCOL = "jar";

    public static final String FILE_PROTOCOL = "file";


    public static Map<String, RpcServiceModel> RpcServerScan(String scanPackage) {
        try {
            List<String> classNameList = getClassNameList(scanPackage);
            if (classNameList == null || classNameList.size() ==0) {
                return new HashMap<>(0);
            }
            Map<String, RpcServiceModel> result = new HashMap<>();
            classNameList.stream().forEach(className -> {
                try {
                    Class<?> clazz = Class.forName(className);
                    RpcService rpcService = clazz.getAnnotation(RpcService.class);
                    if (rpcService != null) {
                        RpcServiceModel rpcServiceModel = new RpcServiceModel();

                        //优先使用interfaceClass, interfaceClass的name为空，再使用interfaceClassName
                        //TODO 后续逻辑向注册中心注册服务元数据，同时向handlerMap中记录标注了RpcService注解的类实例
                        //handlerMap中的Key先简单存储为serviceName+version+group，后续根据实际情况处理key
                        String serviceName = getServiceName(rpcService);
                        String serviceKey = CommonUtil.buildServiceKey(rpcService.group(),serviceName,rpcService.version());
                       //此处可能有问题，newInstance不一定满足所有场景
                        rpcServiceModel.setRpcService(rpcService);
                        rpcServiceModel.setInstance( clazz.newInstance());
                        result.put(serviceKey, rpcServiceModel);
                    }

                } catch (Throwable e) {
                    LOGGER.error("RpcSingleServer init error", e);
                }
            });
            return result;
        } catch (Throwable t) {
            LOGGER.error("RpcSingleServer init error", t);
            return new HashMap<>(0);
        }
    }


    public static Map<String, Object> RpcReferenceScan(String scanPackage) {
        try {
            List<String> classNameList = getClassNameList(scanPackage);
            if (classNameList == null || classNameList.size() ==0) {
                return new HashMap<>(0);
            }
            Map<String, Object> result = new HashMap<>();
            classNameList.stream().forEach(className -> {
                try {
                    Class<?> clazz = Class.forName(className);
                    Field[] declaredFields = clazz.getDeclaredFields();
                    Stream.of(declaredFields).forEach((field) -> {
                        RpcReference rpcReference = field.getAnnotation(RpcReference.class);
                        if (rpcReference != null) {
                            result.put(field.getName(), clazz.getName());
                        }
                    });


                } catch (Throwable e) {
                    LOGGER.error("RpcSingleServer init error", e);
                }
            });
            return result;
        } catch (Throwable t) {
            LOGGER.error("RpcSingleServer init error", t);
            return new HashMap<>(0);
        }
    }


    /**
     * 获取指定包下的所有class列表
     *
     * @param packageName
     * @return
     * @throws Throwable
     */
    public static List<String> getClassNameList(String packageName) throws Throwable {
        List<String> resultList = new LinkedList<>();
        boolean isScanChildFile = true;
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packageName.replace('.', '/'));
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            String protocol = url.getProtocol();
            if (FILE_PROTOCOL.equals(protocol)) {
                List<String> classNameList = findClassNameListByDir(isScanChildFile,packageName, url.getPath());
                if (classNameList != null && classNameList.size() > 0) {
                    resultList.addAll(classNameList);
                }
            } else if (JAR_PROTOCOL.equals(protocol)) {
                List<String> classNameList = findClassNameListByJar(url);
                if (classNameList != null && classNameList.size() > 0) {
                    resultList.addAll(classNameList);
                }
            }
        }
        return resultList;
    }



    /**
     * 获取serviceName
     */
    private static String getServiceName(RpcService rpcService){
        //优先使用interfaceClass
        Class clazz = rpcService.interfaceClass();
        if (clazz == void.class){
            return rpcService.interfaceName();
        }
        String serviceName = clazz.getName();
        if (serviceName == null || serviceName.trim().isEmpty()){
            return rpcService.interfaceName();
        }
        return serviceName;
    }



    /**
     * 获取指定包下的所有class列表
     *
     * @param isScanChildFile 是否扫描子文件夹
     * @param packagePath     文件package路径
     * @return 类名列表
     */
    private static List<String> findClassNameListByDir(boolean isScanChildFile,String packageName, String packagePath) {
        File dirFile = new File(packagePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return null;
        }
        File[] files = dirFile.listFiles((file) -> {
            return file.getName().endsWith(CLASS_SUFFIX) || (isScanChildFile && file.isDirectory());
        });
        if (files == null || files.length == 0) {
            return null;
        }
        List<String> result = new LinkedList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                List<String> childFileList = findClassNameListByDir(isScanChildFile,packageName+"."+file.getName(), file.getAbsolutePath());
                if (childFileList != null && childFileList.size() > 0) {
                    result.addAll(childFileList);
                }
            } else {
                result.add(packageName+'.'+file.getName().substring(0, file.getName().length() - CLASS_SUFFIX.length()));
            }
        }
        return result;
    }


    /**
     * 获取指定jar包下的所有class列表
     * //todo 未实现完整
     *
     * @param url
     * @return
     */
    private static List<String> findClassNameListByJar(URL url) throws IOException {
        JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String name = jarEntry.getName();
            if (name.charAt(0) == '/') {
                name = name.substring(1);
            }
        }
        return null;
    }

}
