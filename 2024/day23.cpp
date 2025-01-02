#include <iostream>
#include <fstream>
#include <string>
#include <vector>
using namespace std;

string part2(vector<string> data) {
    vector<string> max;
    for (int i = 0; i < data.size(); i++) {
        vector<string> others;
        for (int j = i+1; j < data.size(); j++) {
            if (data.at(j).substr(0,2) == data.at(i).substr(0,2))
                others.push_back(data.at(j).substr(3,2));
            else if (data.at(j).substr(3,2) == data.at(i).substr(0,2))
                others.push_back(data.at(j).substr(0,2));
        }
        if (others.size()+1 < max.size())
            continue;
        bool match = true;
        for (int i = 0; i < others.size(); i++)
            for (int j = i+1; j < others.size(); j++)
                if (find(data.begin(), data.end(), others.at(i)+"-"+others.at(j)) == data.end() && find(data.begin(), data.end(), others.at(j)+"-"+others.at(i)) == data.end()) {
                    match = false;
                    break;
                }
        if (!match)
            continue;
        max = others;
        max.push_back(data.at(i).substr(0,2));
    }
    sort(max.begin(), max.end(), [](const string &a, const string &b) -> bool{return a < b;});
    string result;
    for (int i = 0; i < max.size()-1; i++)
        result += max.at(i) + ",";
    result += max.at(max.size()-1);
    return result;
}

int part1(vector<string> data) {
    int result = 0;
    for (int i = 0; i < data.size(); i++) {
        vector<string> others;
        for (int j = i+1; j < data.size(); j++) {
            if (data.at(j).substr(0,2) == data.at(i).substr(0,2))
                others.push_back(data.at(j).substr(3,2));
            else if (data.at(j).substr(3,2) == data.at(i).substr(0,2))
                others.push_back(data.at(j).substr(0,2));
        }
        for (int j = i+1; j < data.size(); j++)
            if (data.at(j).find(data.at(i).substr(3,2)) != string::npos && (find(others.begin(), others.end(), data.at(j).substr(0,2)) != others.end() || find(others.begin(), others.end(), data.at(j).substr(3,2)) != others.end()) && (data.at(j).at(0) == 't' || data.at(j).at(3) == 't' || data.at(i).at(0) == 't' || data.at(i).at(3) == 't'))
                result++;
    }
    return result;
}

int main() {
    string line;
    ifstream input("day23.txt");
    vector<string> data;
    while (getline(input, line))
        data.push_back(line);
    input.close();
    cout << part1(data) << endl;
    cout << part2(data) << endl;
    return 0;
}