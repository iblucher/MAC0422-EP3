JC = javac

.SUFFIXES: .java .class

.java.class:
		$(JC) -cp .:lib.jar $*.java

CLASSES = \
	  Simulator.java \
      Process.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class

run:
	java -cp .:lib.jar Simulator
