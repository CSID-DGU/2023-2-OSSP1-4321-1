import matplotlib.pyplot as plt
import csv

test_data = input("데이터 파일을 입력해주세요: ")

x1 = []
x2 = []
y1 = []
y2 = []

with open(test_data, encoding='utf-8') as file:
    rdr = csv.reader(file)
    for line in rdr:
        if(line[0] == "pass"):
            x1.append(float(line[1]))
        else:
            y1.append(float(line[1]))

for i in x1:
    x2.append(0)

for i in y1:
    y2.append(0)

x1.sort()
y1.sort()

plt.figure(figsize=(1,1))
plt.scatter(x1, x2, color='blue', s=2)
plt.scatter(y1, y2, color='orange', s=2)

fig, ax = plt.subplots()
ax.boxplot([x1, y1])
plt.show()