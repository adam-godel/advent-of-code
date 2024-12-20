#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <queue>
#include <map>
using namespace std;

int bfsCheat(vector<vector<char>> grid, int si, int sj, int ei, int ej, int maxCheat) {
    const int dirs[4][2] = {{1,0},{0,-1},{-1,0},{0,1}};
    queue<array<int,2>> queue;
    queue.push({si,sj});
    map<array<int,2>, int> path;
    while (!queue.empty()) {
        array<int,2> pos = queue.front();
        queue.pop();
        if (path.find(pos) != path.end())
            continue;
        path.insert({pos, path.size()});
        if (pos.at(0) == ei && pos.at(1) == ej)
            break;
        for (int k = 0; k < 4; k++)
            if (grid.at(pos.at(0)+dirs[k][0]).at(pos.at(1)+dirs[k][1]) != '#')
                queue.push({pos.at(0)+dirs[k][0], pos.at(1)+dirs[k][1]});
    }
    int result = 0;
    for (const auto &k: path) {
        array<int,2> pos = k.first;
        for (int i = -maxCheat; i <= maxCheat; i++) {
            int dj = maxCheat-abs(i);
            for (int j = -dj; j <= dj; j++) {
                int cheatLength = abs(i)+abs(j);
                if (path.find({pos.at(0)+i, pos.at(1)+j}) != path.end() && path.at({pos.at(0)+i, pos.at(1)+j})-path.at(pos)-cheatLength >= 100)
                    result++;
            }
        }
    }
    return result;
}

int part2(vector<vector<char>> grid) {
    int si, sj, ei, ej;
    for (int i = 0; i < grid.size(); i++)
        for (int j = 0; j < grid.at(i).size(); j++)
            if (grid.at(i).at(j) == 'S')
                si = i, sj = j;
            else if (grid.at(i).at(j) == 'E')
                ei = i, ej = j;
    return bfsCheat(grid, si, sj, ei, ej, 20);
}

int part1(vector<vector<char>> grid) {
    int si, sj, ei, ej;
    for (int i = 0; i < grid.size(); i++)
        for (int j = 0; j < grid.at(i).size(); j++)
            if (grid.at(i).at(j) == 'S')
                si = i, sj = j;
            else if (grid.at(i).at(j) == 'E')
                ei = i, ej = j;
    return bfsCheat(grid, si, sj, ei, ej, 2);
}

int main() {
    string line;
    ifstream input("day20.txt");
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