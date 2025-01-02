#include <iostream>
#include <fstream>
#include <string>
#include <vector>
using namespace std;

bool check(vector<int> line, int before, int after) {
    bool seenBefore = false;
    for (int i: line) {
        if (i == before) {
            seenBefore = true;
        } else if (i == after) {
            return find(line.begin(), line.end(), before) != line.end() ? seenBefore : true;
        }
    }
    return true;
}

bool checkAll(vector<vector<int>> rules, vector<int> instr) {
    for (vector<int> j: rules) {
        if (!check(instr, j.at(0), j.at(1))) {
            return false;
        }
    }
    return true;
}

int part1(vector<vector<int>> rules, vector<vector<int>> instr) {
    int result = 0;
    for (vector<int> i: instr) {
        if (checkAll(rules, i)) {
            result += i.at(i.size()/2);
        }
    }
    return result;
}

int part2(vector<vector<int>> rules, vector<vector<int>> instr) {
    int result = 0;
    for (vector<int> i: instr) {
        if (checkAll(rules, i)) {
            continue;
        }
        for (int k: i) {
            int before = 0, after = 0;
            for (vector<int> j: rules) {
                if (j.at(0) == k && find(i.begin(), i.end(), j.at(1)) != i.end()) {
                    before++;
                } else if (j.at(1) == k && find(i.begin(), i.end(), j.at(0)) != i.end()) {
                    after++;
                }
            }
            if (before == after) {
                result += k;
            }
        }
    }
    return result;
}

int main() {
    string line;
    ifstream input("day5.txt");
    vector<vector<int>> rules;
    vector<vector<int>> instr;
    bool onRules = true;
    while (getline(input, line)) {
        if (line.size() == 0) {
            onRules = false;
            continue;
        }
        if (onRules) {
            vector<int> rule = {stoi(line.substr(0, line.find('|'))), stoi(line.substr(line.find('|')+1))};
            rules.push_back(rule);
        } else {
            vector<int> oneInstr;
            while (line.find(',') != string::npos) {
                oneInstr.push_back(stoi(line.substr(0, line.find(','))));
                line.erase(0, line.find(',')+1);
            }
            oneInstr.push_back(stoi(line));
            instr.push_back(oneInstr);
        }
    }
    input.close();
    cout << part1(rules, instr) << endl;
    cout << part2(rules, instr) << endl;
    return 0;
}