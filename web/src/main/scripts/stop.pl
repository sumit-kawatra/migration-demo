#!/usr/bin/perl
use strict;

$|=1;

my $PROCESS_PATTERN="DNAME=hawthorne-server";

my @pids = getPid();
foreach my $pid (@pids) {
        print "terminating $pid\n";
        kill 15, $pid;
}

sub getPid {
        my @ps_output = `/bin/ps -ef | grep '$PROCESS_PATTERN' | grep -v grep 2>/opt/logs/hawthorne/master.out`;
        my @pids;

        foreach my $line (@ps_output) {
            my ($pid) = $line =~ /^.+?\s+?(\d+)/;
            push @pids, $pid;
        }
        return @pids;
}
