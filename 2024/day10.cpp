#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <array>
#include <stack>
using namespace std;

int part2(vector<vector<int>> grid) {
    int result = 0;
    for (int i = 0; i < grid.size(); i++) {
        for (int j = 0; j < grid.at(i).size(); j++) {
            if (grid.at(i).at(j) != 0)
                continue;
            const int dirs[4][2] = {{-1,0},{0,1},{1,0},{0,-1}};
            stack<array<int,2>> stack;
            stack.push({i,j});
            while (!stack.empty()) {
                array<int,2> pt = stack.top();
                stack.pop();
                if (grid.at(pt[0]).at(pt[1]) == 9)
                    result++;
                for (int i = 0; i < 4; i++)
                    if (pt[0]+dirs[i][0] >= 0 && pt[0]+dirs[i][0] < grid.size() && 
                        pt[1]+dirs[i][1] >= 0 && pt[1]+dirs[i][1] < grid.at(pt[0]).size() && 
                        grid.at(pt[0]+dirs[i][0]).at(pt[1]+dirs[i][1]) == grid.at(pt[0]).at(pt[1])+1)
                        stack.push({pt[0]+dirs[i][0], pt[1]+dirs[i][1]});
            }
        }
    }
    return result;
}

int part1(vector<vector<int>> grid) {
    int result = 0;
    for (int i = 0; i < grid.size(); i++) {
        for (int j = 0; j < grid.at(i).size(); j++) {
            if (grid.at(i).at(j) != 0)
                continue;
            const int dirs[4][2] = {{-1,0},{0,1},{1,0},{0,-1}};
            vector<array<int,2>> paths;
            stack<array<int,2>> stack;
            stack.push({i,j});
            while (!stack.empty()) {
                array<int,2> pt = stack.top();
                stack.pop();
                if (grid.at(pt[0]).at(pt[1]) == 9 && find(paths.begin(), paths.end(), array<int,2>{pt[0],pt[1]}) == paths.end())
                    paths.push_back({pt[0],pt[1]});
                for (int i = 0; i < 4; i++)
                    if (pt[0]+dirs[i][0] >= 0 && pt[0]+dirs[i][0] < grid.size() && 
                        pt[1]+dirs[i][1] >= 0 && pt[1]+dirs[i][1] < grid.at(pt[0]).size() && 
                        grid.at(pt[0]+dirs[i][0]).at(pt[1]+dirs[i][1]) == grid.at(pt[0]).at(pt[1])+1)
                        stack.push({pt[0]+dirs[i][0], pt[1]+dirs[i][1]});
            }
            result += paths.size();
        }
    }
    return result;
}

int main() {
    string line;
    ifstream input("day10.txt");
    vector<vector<int>> grid;
    while (getline(input, line)) {
        vector<char> lineChars(line.begin(), line.end());
        vector<int> row;
        for (char c: lineChars)
            row.push_back(c-'0');
        grid.push_back(row);
    }
    input.close();
    cout << part1(grid) << endl;
    cout << part2(grid) << endl;
    return 0;
}