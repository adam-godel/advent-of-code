#include <iostream>
#include <fstream>
#include <string>
#include <vector>
using namespace std;

long part2(vector<int> data) {
    vector<int> toSum(data);
    long result = 0;
    for (int j = data.size()-1; j > 0; j -= 2) {
        int i = 1;
        while (i < j && data.at(i) < data.at(j)) {
            i += 2;
        }
        if (i >= j) {
            continue;
        }
        int sum = 0;
        for (int k = 0; k < i; k++) {
            sum += toSum.at(k);
        }
        sum += toSum.at(i)-data.at(i);
        for (int k = sum; k < sum+data.at(j); k++) {
            result += k*j/2;
        }
        data.at(i) -= data.at(j);
        data.at(j) = 0;
    }
    for (int i = 0; i < data.size(); i += 2) {
        int sum = 0;
        for (int k = 0; k < i; k++) {
            sum += toSum.at(k);
        }
        for (int k = sum; k < sum+data.at(i); k++) {
            result += k*i/2;
        }
    }
    return result;
}

long part1(vector<int> data) {
    int i = 0, j = data.size()-1, pos = 0;
    long result = 0;
    while (i <= j) {
        if (i % 2 == 0) {
            for (int k = 0; k < data.at(i); k++) {
                result += (pos++)*i/2;
            }
            i++;
        } else {
            if (data.at(j) > data.at(i)) {
                for (int k = 0; k < data.at(i); k++) {
                    result += (pos++)*j/2;
                }
                data.at(j) -= data.at(i);
                i++;
            } else {
                for (int k = 0; k < data.at(j); k++) {
                    result += (pos++)*j/2;
                }
                data.at(i) -= data.at(j);
                j -= 2;
            }
        }
    }
    return result;
}

int main() {
    string line;
    ifstream input("day9.txt");
    vector<int> data;
    while (getline(input, line)) {
        vector<char> lineChars(line.begin(), line.end());
        for (char c: lineChars) {
            data.push_back(c-'0');
        }
    }
    input.close();
    cout << part1(data) << endl;
    cout << part2(data) << endl;
    return 0;
}