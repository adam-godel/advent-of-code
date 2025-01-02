#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <array>
#include <queue>
using namespace std;

int bfs(vector<vector<bool>> grid) {
    const int dirs[4][2] = {{1,0},{0,-1},{-1,0},{0,1}};
    queue<array<int,3>> queue;
    queue.push({0,0,0});
    vector<array<int,2>> prev;
    while (!queue.empty()) {
        array<int,3> pos = queue.front();
        queue.pop();
        if (find(prev.begin(), prev.end(), array<int,2>{pos.at(0), pos.at(1)}) != prev.end())
            continue;
        prev.push_back({pos.at(0), pos.at(1)});
        if (pos.at(0) == grid.size()-1 && pos.at(1) == grid.size()-1)
            return pos.at(2);
        for (int k = 0; k < 4; k++)
            if (pos.at(0)+dirs[k][0] >= 0 && pos.at(0)+dirs[k][0] < grid.size() && pos.at(1)+dirs[k][1] >= 0 && pos.at(1)+dirs[k][1] < grid.at(pos.at(0)).size() && !grid.at(pos.at(0)+dirs[k][0]).at(pos.at(1)+dirs[k][1]))
                queue.push({pos.at(0)+dirs[k][0], pos.at(1)+dirs[k][1], pos.at(2)+1});
    }
    return -1;
}

string part2(vector<array<int,2>> points) {
    vector<vector<bool>> grid(71, vector<bool>(71));
    int i = 0;
    for (; i < 1024; i++)
        grid.at(points.at(i).at(1)).at(points.at(i).at(0)) = true;
    for (; i < points.size(); i++) {
        grid.at(points.at(i).at(1)).at(points.at(i).at(0)) = true;
        if (bfs(grid) == -1)
            break;
    }
    return to_string(points.at(i).at(0)) + "," + to_string(points.at(i).at(1));
}

int part1(vector<array<int,2>> points) {
    vector<vector<bool>> grid(71, vector<bool>(71));
    for (int i = 0; i < 1024; i++)
        grid.at(points.at(i).at(1)).at(points.at(i).at(0)) = true;
    return bfs(grid);
}

int main() {
    string line;
    ifstream input("day18.txt");
    vector<array<int,2>> points;
    while (getline(input, line)) {
        istringstream s(line);
        string token1, token2;
        getline(s, token1, ','), getline(s, token2, ',');
        points.push_back({stoi(token1), stoi(token2)});
    }
    input.close();
    cout << part1(points) << endl;
    cout << part2(points) << endl;
}