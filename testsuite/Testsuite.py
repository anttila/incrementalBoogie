__author__ = 'Bork'

import time
import subprocess

number_of_tests = 10

tests = [
  #  ["test.bpl","test2.bpl","test3.bpl"],
  #  ["test.bpl","test2.bpl","test3.bpl"],
  #  ["test.bpl","test2.bpl","test3.bpl"]
    ["bunchofifs/iftest1.bpl","bunchofifs/iftest2.bpl","bunchofifs/iftest3.bpl","bunchofifs/iftest4.bpl","bunchofifs/iftest5.bpl"], #DONE
  #  ["LargeAssignments/1.bpl","LargeAssignments/2.bpl","LargeAssignments/3.bpl","LargeAssignments/8.bpl","LargeAssignments/9.bpl"], #DONE
 #   ["LargeAssignmentsDoubleTheProcedures/1.bpl","LargeAssignmentsDoubleTheProcedures/2.bpl","LargeAssignmentsDoubleTheProcedures/3.bpl","LargeAssignmentsDoubleTheProcedures/8.bpl","LargeAssignmentsDoubleTheProcedures/9.bpl"], #DONE
  #  ["MassWhile/massWhile-1.bpl","MassWhile/massWhile-2.bpl","MassWhile/massWhile-3.bpl","MassWhile/massWhile-4.bpl","MassWhile/massWhile-5.bpl"],
  #  ["Arithmetics/Arithmetics-1.bpl","Arithmetics/Arithmetics-2.bpl","Arithmetics/Arithmetics-3.bpl"],
  #  ["Alarm/Alarm-1.bpl","Alarm/Alarm-2.bpl","Alarm/Alarm-3.bpl"], #DONE
  #  ["NestedIf/nestedIf-1.bpl","NestedIf/nestedIf-2.bpl","NestedIf/nestedIf-3.bpl"]
  #  ["Nestedif-2/nestedIf2-1.bpl","Nestedif-2/nestedIf2-1.bpl","Nestedif-2/nestedIf2-1.bpl"]
  # ["bunchofifs-2/iftest1-2.bpl","bunchofifs-2/iftest2-2.bpl","bunchofifs-2/iftest3-2.bpl"], #DONE
]


def average_time(ts):
    import numpy
    d = numpy.array(ts)
    # mean of all values that are within 2 stddev from the mean
    avg = float(numpy.mean(d[abs(d - numpy.mean(d)) <= 2*numpy.std(d)]))
    return avg


if __name__ == "__main__":

    # call external process
    inc_results = []
    inc_results_avg = []
    noninc_results = []
    noninc_results_avg = []
    counter = 0
    print "INCREMENTAL TESTING"
    for test in tests:
        testtimes = []
        # Incremental testing:
        for x in xrange(0,number_of_tests):
            program = ["java", "-classpath", "\".;incrementalBoogie-0.0.1-SNAPSHOT-jar-with-dependencies.jar;com.microsoft.z3.jar\"", "incrementalBoogie.TestMain"]
            program.extend(test)
            start = time.time()
            try:
                output = subprocess.check_output(program)
                print output
            except subprocess.CalledProcessError as e:
                # log error for debugging
                print "ERROR"

            end = time.time()
            testtimes.append(end-start)
        inc_results.append(testtimes)
        inc_results_avg.append(("Test"+str(counter), average_time(testtimes)))
        counter += 1

    counter = 0
    print "NON-INCREMENTAL TESTING"
    # Nonincremental
    for test in tests:
        testtimes = []
        # Non-incremental testing:
        for x in xrange(0,number_of_tests):
            program = ["java", "-classpath", "\".;incrementalBoogie-0.0.1-SNAPSHOT-jar-with-dependencies.jar;com.microsoft.z3.jar\"", "incrementalBoogie.TestMain", "--incrementalityOff"]
            program.extend(test)
            start = time.time()
            try:
                output = subprocess.check_output(program)
                print output
            except subprocess.CalledProcessError as e:
                # log error for debugging
                print "ERROR"

            end = time.time()
            testtimes.append(end-start)
        noninc_results.append(testtimes)
        noninc_results_avg.append(("Test"+str(counter), average_time(testtimes)))
        counter += 1


#    print tests
#    print testtimes
#    print results_avg

    print "Incremental results:"
    for x in inc_results_avg:
        print x[0] + " avg. time of: " + str(x[1])
    print "Non-incremental results:"
    for x in noninc_results_avg:
        print x[0] + " avg. time of: " + str(x[1])
    print "Full results incremental:"
    print inc_results
    print "Full results non-incremental:"
    print noninc_results