#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <map>
using namespace std;

vector<string> split(const string &s, char delim) {
    vector<string> result;
    stringstream ss(s);
    string item;
    while (getline(ss, item, delim)) {
        result.push_back(item.substr(item.find_first_not_of(' '), item.find_first_of(' ', item.find_first_not_of(' '))));
    }
    return result;
}

bool find(vector<string> &instr, string &data, map<string,bool> &cache) {
    if (data.size() == 0)
        return true;
    if (cache.find(data) != cache.end())
        return cache.at(data);
    for (string s: instr) {
        auto match = mismatch(s.begin(), s.end(), data.begin());
        if (match.first == s.end()) {
            data = data.substr(s.size());
            if (find(instr, data, cache)) {
                cache.insert({data, true});
                return true;
            }
            data = s+data;
        }
    }
    cache.insert({data, false});
    return false;
}

long findCounts(vector<string> &instr, string &data, map<string,long> &cache) {
    if (cache.find(data) != cache.end())
        return cache.at(data);
    for (string s: instr) {
        auto match = mismatch(s.begin(), s.end(), data.begin());
        if (match.first == s.end()) {
            data = data.substr(s.size());
            if (findCounts(instr, data, cache)) {
                if (cache.find(s+data) == cache.end())
                    cache.insert({s+data, 0});
                cache.at(s+data) += cache.at(data);
            }
            data = s+data;
        }
    }
    if (cache.find(data) == cache.end())
        cache.insert({data, 0});
    return cache.at(data);
}

long part2(vector<string> data, vector<string> instr) {
    long result = 0;
    for (string s: data) {
        map<string,long> cache;
        cache.insert({"", 1});
        result += findCounts(instr, s, cache);
    }
    return result;
}

int part1(vector<string> data, vector<string> instr) {
    int result = 0;
    for (string s: data) {
        map<string,bool> cache;
        result += find(instr, s, cache);
    }
    return result;
}

int main() {
    string line;
    ifstream input("day19.txt");
    getline(input, line);
    vector<string> instr = split(line, ','), data;
    getline(input, line);
    while (getline(input, line))
        data.push_back(line);
    input.close();
    cout << part1(data, instr) << endl;
    cout << part2(data, instr) << endl;
}