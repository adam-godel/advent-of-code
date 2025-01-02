#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <vector>
using namespace std;

bool check(vector<vector<char>> data, int i, int j, int di, int dj, string word) {
    for (char k: word) {
        if (i < 0 || i >= data.size() || j < 0 || j >= data.at(i).size() || data.at(i).at(j) != k)
            return false;
        i += di, j += dj;
    }
    return true;
}

int part1(vector<vector<char>> data) {
    vector<vector<int>> dir = {{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1},{0,1},{1,1}};
    int result = 0;
    for (int i = 0; i < data.size(); i++)
        for (int j = 0; j < data.at(i).size(); j++)
            for (vector<int> d: dir)
                if (check(data, i, j, d.at(0), d.at(1), "XMAS"))
                    result++;
    return result;
}

int part2(vector<vector<char>> data) {
    vector<vector<int>> dir = {{1,-1},{-1,-1},{-1,1},{1,1}};
    int result = 0;
    for (int i = 0; i < data.size(); i++) {
        for (int j = 0; j < data.at(i).size(); j++) {
            if (data.at(i).at(j) != 'A')
                continue;
            int M = 0, S = 0;
            for (vector<int> d: dir) {
                int pi = i+d.at(0), pj = j+d.at(1);
                if (pi >= 0 && pi < data.size() && pj >= 0 && pj < data.at(pi).size()) {
                    if (data.at(pi).at(pj) == 'M')
                        M++;
                    else if (data.at(pi).at(pj) == 'S')
                        S++;
                }
            }
            if (M == 2 && S == 2 && data.at(i-1).at(j-1) != data.at(i+1).at(j+1))
                result++;
        }
    }
    return result;
}

int main() {
    string line;
    ifstream input("day4.txt");
    vector<vector<char>> data; 
    while (getline(input, line)) {
        vector<char> lineChars(line.begin(), line.end());
        data.push_back(lineChars);
    }
    input.close();
    cout << part1(data) << endl;
    cout << part2(data) << endl;
    return 0;
}