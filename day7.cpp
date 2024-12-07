#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
using namespace std;

vector<long> split (const string &s, char delim) {
    vector<long> result;
    stringstream ss(s);
    string item;
    while (getline(ss, item, delim)) {
        result.push_back(stol(item));
    }
    return result;
}

bool checkOne(vector<long> line, bitset<16> ops) {
    long result = line.at(1);
    for (int i = 2; i < line.size(); i++) {
        if (ops[i-1]) {
            result *= line.at(i);
        } else {
            result += line.at(i);
        }
    }
    return result == line.at(0);
}

bool checkLine(vector<long> line) {
    for (int i = 0; i < pow(2, line.size()-1); i++) {
        bitset<16> ops(i);
        if (checkOne(line, ops)) {
            return true;
        }
    }
    return false;
}

bool checkConcatOne(vector<long> line, int ops[]) {
    long result = line.at(1);
    for (int i = 2; i < line.size(); i++) {
        if (ops[i-1] == 2) {
            result = stol(to_string(result)+to_string(line.at(i)));
        } else if (ops[i-1] == 1) {
            result *= line.at(i);
        } else {
            result += line.at(i);
        }
    }
    return result == line.at(0);
}

bool checkConcatLine(vector<long> line) {
    for (int i = 0; i < pow(3, line.size()-1); i++) {
        int ops[line.size()-1];
        for (int k = 0; k < sizeof(ops); k++) {
            ops[k] = 0;
        }
        int idx = 0, j = i;
        while (j > 0) {
            ops[idx++] = j % 3;
            j /= 3;
        }
        if (checkConcatOne(line, ops)) {
            return true;
        }
    }
    return false;
}

long part1(vector<vector<long>> data) {
    long result = 0;
    for (vector<long> i: data) {
        if (checkLine(i)) {
            result += i.at(0);
        }
    }
    return result;
}

long part2(vector<vector<long>> data) {
    long result = 0;
    for (vector<long> i: data) {
        if (checkConcatLine(i)) {
            result += i.at(0);
        }
    }
    return result;
}

int main() {
    string line;
    ifstream input("day7.txt");
    vector<vector<long>> data;
    while (getline(input, line)) {
        line.erase(find(line.begin(), line.end(), ':'));
        data.push_back(split(line, ' '));
    }
    input.close();
    cout << part1(data) << endl;
    cout << part2(data) << endl;
    return 0;
}