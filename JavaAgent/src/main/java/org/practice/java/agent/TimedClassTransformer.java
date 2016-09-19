package org.practice.java.agent;

import javassist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class TimedClassTransformer implements ClassFileTransformer {
    private static final Logger LOG = LoggerFactory.getLogger(TimedClassTransformer.class);
    private final ClassPool classPool;

    public TimedClassTransformer() {
        classPool = new ClassPool();
        classPool.appendSystemPath();

        try {
            classPool.appendPathList(System.getProperty("java.class.path"));
            classPool.get("org.practice.java.agent.MetricReporter").getClass();
            classPool.appendClassPath(new LoaderClassPath(ClassLoader.getSystemClassLoader()));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Initialised TimedClassTransformer");
    }

    @Override
    public byte[] transform(ClassLoader loader,
                            String fullyQualifiedClassName,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        String className = fullyQualifiedClassName.replace("/", ".");
        System.out.println("Transforming ClassName " + className);
        classPool.appendClassPath(new ByteArrayClassPath(className, classfileBuffer));

        try {
            CtClass ctClass = classPool.get(className);
            if (ctClass.isFrozen()) {
                System.out.println("Skip. class {} is frozen: "+ className);
                return null;
            }

            if (ctClass.isPrimitive() ||
                    ctClass.isAnnotation() ||
                    ctClass.isArray() ||
                    ctClass.isEnum() ||
                    ctClass.isInterface() ) {
                System.out.println("Skip. Class {} not a class: "+ className);
                return null;
            }

            boolean isCtclassModified = false;
            for (CtMethod method : ctClass.getDeclaredMethods()) {
                if (method.hasAnnotation(Measured.class)) {
                    if (method.getMethodInfo().getCodeAttribute() == null) {
                        System.out.println("Skip method "+ method.getLongName());
                        continue;
                    }

                    System.out.println("Instrumenting method "+ method.getLongName());
                    try {
                        method.addLocalVariable("__metricStartTime", CtClass.longType);
                        method.insertBefore("" +
                                "__metricStartTime = System.currentTimeMillis();\n" + "");
                        String metricName = ctClass.getName() + "." + method.getName();
                        method.insertAfter(
                                "org.practice.java.agent.MetricReporter.reportTime("
                                        +"\""
                                        + metricName
                                        + "\""
                                        +", System.currentTimeMillis() - __metricStartTime);");
                        isCtclassModified = true;
                    } catch (CannotCompileException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (isCtclassModified) {
                return ctClass.toBytecode();
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classfileBuffer;
    }
}
