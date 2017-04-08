from sklearn import svm
import numpy as np
import sys
import MySQLdb
import os

days = int(sys.argv[1]);
txtId = int(sys.argv[2]);
stockCode = str(sys.argv[3]);
#days = 4;
#txtId = 1;
#stockCode = "MSFT";
SQLcon = MySQLdb.connect(
        host = '127.0.0.1',
        port = 3306,
        user = 'root',
        passwd = 'root',
        db = 'StockInformation');
cursor = SQLcon.cursor();

high_train = [];
low_train = [];
open_train = [];
close_train = [];

high_lines = cursor.execute("select high from "+stockCode);
high_result = cursor.fetchmany(high_lines);
for i in high_result:
    high_train.append(i);

low_lines = cursor.execute("select low from "+stockCode);
low_result = cursor.fetchmany(low_lines);
for i in low_result:
    low_train.append(i);

open_lines = cursor.execute("select open from "+stockCode);
open_result = cursor.fetchmany(open_lines);
for i in open_result:
    open_train.append(i);

close_lines = cursor.execute("select close from "+stockCode);
close_result = cursor.fetchmany(close_lines);
for i in close_result:
    close_train.append(i);

high_test = [];
low_test = [];
open_test = [];
close_test = [];

high_test = high_train[len(high_train)-days:len(high_train)];
high_train = high_train[0:len(high_train)];

low_test = low_train[len(low_train)-days:len(low_train)];
low_train = low_train[0:len(low_train)];

open_test = open_train[len(open_train)-days:len(open_train)];
open_train = open_train[0:len(open_train)];

close_test = close_train[len(close_train)-days:len(close_train)];
close_train = close_train[0:len(close_train)];

high_y = [];
low_y = [];
open_y = [];
close_y = [];

for i in range(0, len(high_train)-1):
    if(high_train[i+1]>high_train[i]):
        high_y.append(1);
    else:
        high_y.append(-1);
if(high_test[0]>high_train[-1]):
    high_y.append(1);
else:
    high_y.append(-1);

for i in range(0, len(low_train)-1):
    if(low_train[i+1]>low_train[i]):
        low_y.append(1);
    else:
        low_y.append(-1);
if(low_test[0]>low_train[-1]):
    low_y.append(1);
else:
    low_y.append(-1);

for i in range(0, len(open_train)-1):
    if(open_train[i+1]>open_train[i]):
        open_y.append(1);
    else:
        open_y.append(-1);
if(open_test[0]>open_train[-1]):
    open_y.append(1);
else:
    open_y.append(-1);

for i in range(0, len(close_train)-1):
    if(close_train[i+1]>close_train[i]):
        close_y.append(1);
    else:
        close_y.append(-1);
if(close_test[0]>close_train[-1]):
    close_y.append(1);
else:
    close_y.append(-1);


high_train = np.array(high_train).reshape((len(high_train),1));
high_test = np.array(high_test).reshape((len(high_test),1));

low_train = np.array(low_train).reshape((len(low_train),1));
low_test = np.array(low_test).reshape((len(low_test),1));

open_train = np.array(open_train).reshape((len(open_train),1));
open_test = np.array(open_test).reshape((len(open_test),1));

close_train = np.array(close_train).reshape((len(close_train),1));
close_test = np.array(close_test).reshape((len(close_test),1));

clf = svm.SVR();
clf.fit(high_train, high_y);
high_result = clf.predict(high_test);

clf.fit(low_train, low_y);
low_result = clf.predict(high_test);

clf.fit(open_train, open_y);
open_result = clf.predict(open_test);

clf.fit(close_train, close_y);
close_result = clf.predict(close_test);

Efile = open("PredText\\"+str(txtId)+"1.txt", 'w');
for i in range(0, len(high_result)):
     a = float(high_test[i]) * (high_result[i]/100+1);
     Efile.write(str(a)+"\n");
Efile.close();

Efile = open("PredText\\"+str(txtId)+"2.txt", 'w');
for i in range(0, len(low_result)):
     a = float(low_test[i]) * (low_result[i]/100+1);
     Efile.write(str(a)+"\n");
Efile.close();

Efile = open("PredText\\"+str(txtId)+"3.txt", 'w');
for i in range(0, len(open_result)):
     a = float(open_test[i]) * (open_result[i]/100+1);
     Efile.write(str(a)+"\n");
Efile.close();

Efile = open("PredText\\"+str(txtId)+"4.txt", 'w');
for i in range(0, len(close_result)):
     a = float(close_test[i]) * (close_result[i]/100+1);
     Efile.write(str(a)+"\n");
Efile.close();

SQLcon.commit();
SQLcon.close();
    

