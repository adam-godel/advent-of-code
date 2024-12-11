#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <map>
using namespace std;

vector<long> split(const string &s, char delim) {
    vector<long> result;
    stringstream ss(s);
    string item;
    while (getline(ss, item, delim)) {
        result.push_back(stol(item));
    }
    return result;
}

vector<long> blink(vector<long> data) {
    for (int i = 0; i < data.size(); i++) {
        string val = to_string(data.at(i));
        if (data.at(i) == 0) {
            data.at(i) = 1;
        } else if (val.size() % 2 == 0) {
            data.at(i++) = stoi(val.substr(0, val.size()/2));
            data.insert(data.begin()+i, stoi(val.substr(val.size()/2, val.size()/2)));
        } else {
            data.at(i) *= 2024;
        }
    }
    return data;
}

int part1(vector<long> data) {
    for (int i = 0; i < 25; i++)
        data = blink(data);
    return data.size();
}

unsigned long part2(vector<long> data) {
    map<long, long> counts;
    for (long l: data)
        counts[l] = 1;
    for (int k = 0; k < 75; k++) {
        map<long, long> newCounts;
        for (auto stone: counts) {
            string val = to_string(stone.first);
            if (stone.first == 0) {
                newCounts.count(1) ? newCounts[1] += stone.second : newCounts[1] = stone.second;
            } else if (val.size() % 2 == 0) {
                newCounts.count(stol(val.substr(0, val.size()/2))) ? newCounts[stol(val.substr(0, val.size()/2))] += stone.second : newCounts[stol(val.substr(0, val.size()/2))] = stone.second;
                newCounts.count(stol(val.substr(val.size()/2, val.size()/2))) ? newCounts[stol(val.substr(val.size()/2, val.size()/2))] += stone.second : newCounts[stol(val.substr(val.size()/2, val.size()/2))] = stone.second;
            } else {
                newCounts.count(stone.first*2024) ? newCounts[stone.first*2024] += stone.second : newCounts[stone.first*2024] = stone.second;
            }
        }
        counts = newCounts;
    }
    unsigned long result = 0;
    for (auto stone: counts)
        result += stone.second;
    return result;
}

int main() {
    string line;
    ifstream input("day11.txt");
    vector<long> data; 
    while (getline(input, line)) {
        data = split(line, ' ');
    }
    input.close();
    cout << part1(data) << endl;
    cout << part2(data) << endl;
    return 0;
}