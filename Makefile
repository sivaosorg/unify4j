.PHONY: build test jar clean

build:
	./gradlew jar

clean:
	./gradlew clean

jar: build

list-task:
	./gradlew tasks

test:
	./gradlew test

groovy:
	./gradlew build

tree:
	# Create logs directory if not exists
	mkdir -p logs
	# Generate project structure and save it to logs/project_structure.txt
	tree -I ".gradle|.idea|build|logs" > ./logs/project_structure.txt
