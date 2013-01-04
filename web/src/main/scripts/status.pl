#!/usr/bin/perl
use strict;

$|=1;

my $PROCESS_PATTERN="DNAME=hawthorne-server";

my @ps_output = `/bin/ps -ef | grep '$PROCESS_PATTERN' | grep -v grep 2>/dev/null`;
my $num_of_processes = scalar(@ps_output);

exit ($num_of_processes != 1)
