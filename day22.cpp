#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <array>
#include <map>
using namespace std;

long part1(vector<long> data) {
    long result = 0;
    for (long val: data) {
        for (int k = 0; k < 2000; k++) {
            val = ((val*64)^val)%16777216;
            val = ((val/32)^val)%16777216;
            val = ((val*2048)^val)%16777216;
        }
        result += val;
    }
    return result;
}

int part2(vector<long> data) {
    map<array<int,4>,int> changeMap;
    for (long val: data) {
        array<int,2000> values;
        map<array<int,4>,bool> found;
        long oldVal = 0;
        for (int k = 0; k < 2000; k++) {
            val = ((val*64)^val)%16777216;
            val = ((val/32)^val)%16777216;
            val = ((val*2048)^val)%16777216;
            values.at(k) = val%10;
        }
        for (int k = 4; k < 2000; k++) {
            array<int,4> changes = {values.at(k-3)-values.at(k-4), values.at(k-2)-values.at(k-3), values.at(k-1)-values.at(k-2), values.at(k)-values.at(k-1)};
            if (changeMap.find(changes) == changeMap.end())
                changeMap.insert({changes, 0});
            if (found.find(changes) != found.end())
                continue;
            changeMap.at(changes) += values.at(k);
            found.insert({changes, true});
        }
    }
    auto max = max_element(changeMap.begin(), changeMap.end(), [](const pair<array<int,4>,int> &a, const pair<array<int,4>,int> &b)->bool{return a.second < b.second;});
    return max->second;
}

int main() {
    string line;
    ifstream input("day22.txt");
    vector<long> data;
    while (getline(input, line))
        data.push_back(stol(line));
    input.close();
    cout << part1(data) << endl;
    cout << part2(data) << endl;
    return 0;
}