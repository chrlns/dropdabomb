import os
env = Environment()

env['ENV']['LANG'] = 'en_GB.UTF-8'
env['JAVACFLAGS']    = '-source 1.6 -target 1.6'
env['JAVACLASSPATH'] = 'xpp3_min-1.1.4c.jar:swing-layout-1.0.3.jar:xstream-1.3.1.jar'

# Build Java classes
kcb_classes = env.Java(target='classes', source=['bomberman'])

# Bundle classes in JAR files
kcbomberman_jar = env.Jar(target='kcbomberman.jar', source=[kcb_classes, 'MANIFEST.MF'])
