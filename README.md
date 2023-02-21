Project Title
com.bear.robotics.SimpleAtmApplication for code challenge.

Build
build with gradle.



Analyzer
It is set as default analyzer with whitespace tokenizer with only alphabet letters and lowercase filter.
It means that it dose not support partial match as default.
If you want, you need to add proper tokenizer for it.


Run
$java -jar ./build/libs/SimpleAtm-0.0.1-SNAPSHOT.jar

Test
$gradlew test