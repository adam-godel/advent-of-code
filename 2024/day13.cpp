#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
using namespace std;

vector<int> split(const string &s, char delim) {
    vector<int> result;
    stringstream ss(s);
    string item;
    while (getline(ss, item, delim)) {
        if (any_of(item.begin(), item.end(), ::isdigit))
            result.push_back(stoi(item.substr(item.find_first_of("0123456789"), item.find_first_not_of("0123456789", item.find_first_of("0123456789")))));
    }
    return result;
}

long part2(vector<vector<int>> data) {
    long result = 0;
    for (vector<int> vals: data) {
        long x1 = vals.at(0), x2 = vals.at(1), y1 = vals.at(2), y2 = vals.at(3), z1 = vals.at(4)+10000000000000, z2 = vals.at(5)+10000000000000;
        if (x1*y2-x2*y1 == 0)
            continue;
        long x = (z1*y2-z2*y1)/(x1*y2-x2*y1);
        long y = (x1*z2-x2*z1)/(x1*y2-x2*y1);
        if (x*x1+y*y1 == z1 && x*x2+y*y2 == z2)
            result += 3*x+y;
    }
    return result;
}

int part1(vector<vector<int>> data) {
    int result = 0;
    for (vector<int> vals: data) {
        int x1 = vals.at(0), x2 = vals.at(1), y1 = vals.at(2), y2 = vals.at(3), z1 = vals.at(4), z2 = vals.at(5);
        if (x1*y2-x2*y1 == 0)
            continue;
        int x = (z1*y2-z2*y1)/(x1*y2-x2*y1);
        int y = (x1*z2-x2*z1)/(x1*y2-x2*y1);
        if (x*x1+y*y1 == z1 && x*x2+y*y2 == z2)
            result += 3*x+y;
    }
    return result;
}

int main() {
    string line;
    ifstream input("day13.txt");
    vector<vector<int>> data;
    vector<int> temp;
    while (getline(input, line)) {
        if (line.size() == 0) {
            data.push_back(temp);
            temp.clear();
            continue;
        }
        line.erase(find(line.begin(), line.end(), ':'));
        vector<int> val = split(line, ' ');
        temp.insert(temp.end(), val.begin(), val.end());
    }
    data.push_back(temp);
    cout << part1(data) << endl;
    cout << part2(data) << endl;
    return 0;
}