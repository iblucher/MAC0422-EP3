JC = javac

.SUFFIXES: .java .class

.java.class:
		$(JC) -cp .:stdlib.jar $*.java

CLASSES = \
	  Simulator.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class

run:
	java -cp .:stdlib.jar Simulator