#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <array>
#include <stack>
using namespace std;

int numCorners(vector<vector<char>> grid, int i, int j) {
    const int dirs[4][2] = {{-1,-1},{1,-1},{1,1},{-1,1}};
    int result = 0;
    for (int k = 0; k < 4; k++) {
        if (((i+dirs[k][0] < 0 || i+dirs[k][0] >= grid.size() || grid.at(i+dirs[k][0]).at(j) != grid.at(i).at(j)) && (j+dirs[k][1] < 0 || j+dirs[k][1] >= grid.at(i).size() || grid.at(i).at(j+dirs[k][1]) != grid.at(i).at(j))) || ((i+dirs[k][0] >= 0 && i+dirs[k][0] < grid.size() && grid.at(i+dirs[k][0]).at(j) == grid.at(i).at(j)) && (j+dirs[k][1] >= 0 && j+dirs[k][1] < grid.at(i).size() && grid.at(i).at(j+dirs[k][1]) == grid.at(i).at(j)) && (i+dirs[k][0] < 0 || i+dirs[k][0] >= grid.size() || j+dirs[k][1] < 0 || j+dirs[k][1] >= grid.at(i).size() || grid.at(i+dirs[k][0]).at(j+dirs[k][1]) != grid.at(i).at(j))))
            result++;
    }
    return result;
}

int part2(vector<vector<char>> grid) {
    vector<vector<bool>> seen(grid.size(), vector<bool>(grid.at(0).size()));
    const int dirs[4][2] = {{1,0},{0,1},{-1,0},{0,-1}};
    int result = 0;
    for (int i = 0; i < grid.size(); i++) {
        for (int j = 0; j < grid.at(i).size(); j++) {
            stack<array<int,2>> stack;
            int area = 0, perim = 0;
            stack.push({i,j});
            while (!stack.empty()) {
                array<int,2> pos = stack.top();
                stack.pop();
                int ci = pos.at(0), cj = pos.at(1);
                if (seen.at(ci).at(cj))
                    continue;
                area++; perim += numCorners(grid, ci, cj);
                seen.at(ci).at(cj) = true;
                for (int k = 0; k < 4; k++) {
                    if (ci+dirs[k][0] >= 0 && ci+dirs[k][0] < grid.size() && cj+dirs[k][1] >= 0 && cj+dirs[k][1] < grid.at(ci).size() && grid.at(ci+dirs[k][0]).at(cj+dirs[k][1]) == grid.at(ci).at(cj))
                        stack.push({ci+dirs[k][0], cj+dirs[k][1]});
                }
            }
            result += area*perim;
        }
    }
    return result;
}

int part1(vector<vector<char>> grid) {
    vector<vector<bool>> seen(grid.size(), vector<bool>(grid.at(0).size()));
    const int dirs[4][2] = {{1,0},{0,1},{-1,0},{0,-1}};
    int result = 0;
    for (int i = 0; i < grid.size(); i++) {
        for (int j = 0; j < grid.at(i).size(); j++) {
            stack<array<int,2>> stack;
            int area = 0, perim = 0;
            stack.push({i,j});
            while (!stack.empty()) {
                array<int,2> pos = stack.top();
                stack.pop();
                int ci = pos.at(0), cj = pos.at(1);
                if (seen.at(ci).at(cj))
                    continue;
                area++;
                seen.at(ci).at(cj) = true;
                for (int k = 0; k < 4; k++) {
                    if (ci+dirs[k][0] >= 0 && ci+dirs[k][0] < grid.size() && cj+dirs[k][1] >= 0 && cj+dirs[k][1] < grid.at(ci).size() && grid.at(ci+dirs[k][0]).at(cj+dirs[k][1]) == grid.at(ci).at(cj))
                        stack.push({ci+dirs[k][0], cj+dirs[k][1]});
                    else
                        perim++;
                }
            }
            result += area*perim;
        }
    }
    return result;
}

int main() {
    string line;
    ifstream input("day12.txt");
    vector<vector<char>> grid;
    while (getline(input, line)) {
        vector<char> lineChars(line.begin(), line.end());
        grid.push_back(lineChars);
    }
    input.close();
    cout << part1(grid) << endl;
    cout << part2(grid) << endl;
    return 0;
}