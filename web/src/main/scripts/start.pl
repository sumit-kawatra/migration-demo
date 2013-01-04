#!/usr/bin/perl

# import environment-specific variables
require 'setup-env.pl';

exec ("(java -classpath hawthorne-server:$JAVA_HOME/lib/tools.jar -DNAME=hawthorne-server EmbeddedJettyServer $HTTP_PORT $JETTY_TMP_DIR &) &>> /opt/logs/hawthorne/master.out");
