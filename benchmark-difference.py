import csv

def getResult(fileName):
    with open(fileName) as f:
        next(f)  # Skip the header
        reader = csv.reader(f, skipinitialspace=True)
        return dict(reader)

headResult = getResult('profile-out/benchmark.csv')
mergeResult = getResult('profile-out-merge/benchmark.csv')

headMean = headResult['mean']
mergeMean = mergeResult['mean']

print "Branch Head Build Time: " + headMean + " | Base Branch Build Time: " + mergeMean



