# banking-swift-messages-java
Parser for Financial SWIFT Messages
SWIFT = Society for Worldwide Interbank Financial Telecommunication





[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)


*********************** TO BE UPDATED START***************************

[![Codacy grade](https://img.shields.io/codacy/grade/d4e120eafc4341aabe1a474aa17008b6.svg)](https://www.codacy.com/app/bengt-brodersen/banking-swift-messages-java)

[![Travis](https://img.shields.io/travis/qoomon/banking-swift-messages-java.svg)](https://travis-ci.org/qoomon/banking-swift-messages-java)

[![Codacy coverage](https://img.shields.io/codacy/coverage/d4e120eafc4341aabe1a474aa17008b6.svg)](https://www.codacy.com/app/bengt-brodersen/banking-swift-messages-java)

[![Snyk Vulnerabilities](https://snyk.io/test/github/qoomon/banking-swift-messages-java/badge.svg)](https://snyk.io/test/github/qoomon/banking-swift-messages-java)

### Releases

[![Release](https://jitpack.io/v/qoomon/banking-swift-messages-java.svg)](https://jitpack.io/#qoomon/banking-swift-messages-java)

*********************** TO BE UPDATED END***************************






Support for MT490 and MT942 so far.
If you need more MT formats just let me know.


## Dev Notes
[SEPA Verwendugszweck Fields](https://www.hettwer-beratung.de/sepa-spezialwissen/sepa-technische-anforderungen/sepa-gesch%C3%A4ftsvorfallcodes-gvc-mt-940/)
* EREF : Ende-zu-Ende Referenz
* KREF : Kundenreferenz
* MREF : Mandatsreferenz
* BREF : Bankreferenz
* RREF : Retourenreferenz
* CRED : Creditor-ID
* DEBT : Debitor-ID
* COAM : Zinskompensationsbetrag
* OAMT : Ursprünglicher Umsatzbetrag
* SVWZ : Verwendungszweck
* ABWA : Abweichender Auftraggeber
* ABWE : Abweichender Empfänger
* IBAN : IBAN des Auftraggebers
* BIC : BIC des Auftraggebers

###### Tags
Banking, Reader, Decoder


**********************************

Temporary fix for jars not available in the HMRC nexus repo. You must have mvn setup in the PATH.

If you have not, you can append the below to your ~/.bash_profile and source the file after update.


    export JAVA_HOME=/opt/java/jdk1.8.0_161
    PATH=$PATH:$JAVA_HOME/bin
    export MAVEN_HOME="/opt/tools/apache-maven-3.5.0/"
    PATH=$PATH:$MAVEN_HOME/bin
    export PATH



Run the setup.sh file in the setup directory to install the required jar files to your local repo
