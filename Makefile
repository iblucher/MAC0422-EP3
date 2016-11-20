JC = javac

.SUFFIXES: .java .class

.java.class:
		$(JC) -cp .:lib.jar $*.java

CLASSES = \
	  Simulator.java \
      Process.java \
      SpaceManagement.java \
      PageReplacement.java \
      PageTable.java \
      OptimalList.java \
      Clock.java \
      LRU.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class

run:
	@java -cp .:lib.jar Simulator
