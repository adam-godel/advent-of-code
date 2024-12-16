#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <array>
#include <queue>
#include <set>
using namespace std;

class Position {
    public:
        int i, j, dir, cost;
        Position* prev;
        Position(int i, int j, int dir, int cost, Position* prev) {
            this->i=i, this->j=j, this->dir=dir, this->cost=cost, this->prev=prev;
        }
};

class Compare {
    public:
        bool operator()(Position* a, Position* b) {
            return a->cost > b->cost;
        }
};

int part2(vector<vector<char>> grid) {
    const int dirs[4][2] = {{1,0},{0,-1},{-1,0},{0,1}};
    priority_queue<Position*, vector<Position*>, Compare> pq;
    pq.push(new Position((int)grid.size()-2,1,3,0,0));
    set<array<int,2>> paths;
    vector<Position> prev;
    while (!pq.empty()) {
        Position* addr = pq.top();
        Position pos = *addr;
        pq.pop();
        if (pos.i == 1 && pos.j == grid.at(pos.i).size()-2) {
            int minCost = pos.cost;
            Position* cur = addr;
            do {
                while (cur != 0) {
                    paths.insert({cur->i,cur->j});
                    cur = cur->prev;
                }
                cur = pq.top();
                pq.pop();
            } while (cur->cost == minCost);
            paths.insert({pos.i,pos.j});
            break;
        }
        bool seen = false;
        for (Position k: prev)
            if (pos.i == k.i && pos.j == k.j && pos.dir == k.dir)
                seen = true;
        if (seen)
            continue;
        prev.push_back(pos);
        for (int k = 0; k < 4; k++)
            if (grid.at(pos.i+dirs[k][0]).at(pos.j+dirs[k][1]) != '#')
                pq.push(new Position(pos.i+dirs[k][0], pos.j+dirs[k][1], k, pos.cost+(pos.dir == k ? 1 : 1001), addr));
    }
    return paths.size();
}

int part1(vector<vector<char>> grid) {
    const int dirs[4][2] = {{1,0},{0,-1},{-1,0},{0,1}};
    priority_queue<Position*, vector<Position*>, Compare> pq;
    pq.push(new Position((int)grid.size()-2,1,3,0,0));
    vector<Position> prev;
    while (!pq.empty()) {
        Position* addr = pq.top();
        Position pos = *addr;
        pq.pop();
        if (pos.i == 1 && pos.j == grid.at(pos.i).size()-2)
            return pos.cost;
        bool seen = false;
        for (Position k: prev)
            if (pos.i == k.i && pos.j == k.j && pos.dir == k.dir)
                seen = true;
        if (seen)
            continue;
        prev.push_back(pos);
        for (int k = 0; k < 4; k++) {
            if (grid.at(pos.i+dirs[k][0]).at(pos.j+dirs[k][1]) != '#') {
                pq.push(new Position(pos.i+dirs[k][0], pos.j+dirs[k][1], k, pos.cost+(pos.dir == k ? 1 : 1001), 0));
            }
        }
    }
    return -1;
}

int main() {
    string line;
    ifstream input("day16.txt");
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