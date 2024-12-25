#include <iostream>
#include <fstream>
#include <string>
#include <vector>
using namespace std;

int part1(vector<vector<vector<char>>> keys, vector<vector<vector<char>>> locks) {
    vector<vector<int>> keyNum, lockNum;
    for (vector<vector<char>> grid: keys) {
        vector<int> temp;
        for (int j = 0; j < grid.at(0).size(); j++) {
            int count = 0;
            for (int i = 1; i < grid.size()-1; i++)
                if (grid.at(i).at(j) == '#')
                    count++;
            temp.push_back(count);
        }
        keyNum.push_back(temp);
    }
    for (vector<vector<char>> grid: locks) {
        vector<int> temp;
        for (int j = 0; j < grid.at(0).size(); j++) {
            int count = 0;
            for (int i = 1; i < grid.size()-1; i++)
                if (grid.at(i).at(j) == '#')
                    count++;
            temp.push_back(count);
        }
        lockNum.push_back(temp);
    }
    int result = 0;
    for (int i = 0; i < keyNum.size(); i++) {
        for (int j = 0; j < lockNum.size(); j++) {
            bool fit = true;
            for (int k = 0; k < keyNum.at(i).size(); k++) {
                if (keyNum.at(i).at(k)+lockNum.at(j).at(k) > 5) {
                    fit = false;
                    break;
                }
            }
            if (fit) result++;
        }
    }
    return result;
}

string part2() {
    return "Deliver the Chronicle!";
}

int main() {
    string line;
    ifstream input("day25.txt");
    vector<vector<vector<char>>> keys, locks;
    vector<vector<char>> temp;
    while (getline(input, line)) {
        if (line.size() == 0) {
            if (find(temp.at(0).begin(), temp.at(0).end(), '#') != temp.at(0).end())
                locks.push_back(temp);
            else
                keys.push_back(temp);
            temp.clear();
            continue;
        }
        vector<char> lineChars(line.begin(), line.end());
        temp.push_back(lineChars);
    }
    if (find(temp.at(0).begin(), temp.at(0).end(), '#') != temp.at(0).end())
        locks.push_back(temp);
    else
        keys.push_back(temp);
    temp.clear();
    input.close();
    cout << part1(keys, locks) << endl;
    cout << part2() << endl;
}