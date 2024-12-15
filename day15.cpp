#include <iostream>
#include <fstream>
#include <string>
#include <vector>
using namespace std;

bool makeMove(vector<vector<char>> &grid, int ci, int cj, int dir) {
    const int dirs[4][2] = {{1,0},{0,-1},{-1,0},{0,1}};
    if ((grid.at(ci+dirs[dir][0]).at(cj+dirs[dir][1]) != '[' || (makeMove(grid, ci+dirs[dir][0], cj+dirs[dir][1]+1, dir) && makeMove(grid, ci+dirs[dir][0], cj+dirs[dir][1], dir))) && (grid.at(ci+dirs[dir][0]).at(cj+dirs[dir][1]) != ']' || (makeMove(grid, ci+dirs[dir][0], cj+dirs[dir][1]-1, dir) && makeMove(grid, ci+dirs[dir][0], cj+dirs[dir][1], dir))) && (grid.at(ci).at(cj) != 'O' || makeMove(grid, ci+dirs[dir][0], cj+dirs[dir][1], dir)) && grid.at(ci+dirs[dir][0]).at(cj+dirs[dir][1]) != '#') {
        char temp = grid.at(ci+dirs[dir][0]).at(cj+dirs[dir][1]);
        grid.at(ci+dirs[dir][0]).at(cj+dirs[dir][1]) = grid.at(ci).at(cj);
        grid.at(ci).at(cj) = temp;
        return true;
    }
    return false;
}

int isValid(vector<vector<char>> grid, int i, int j, char instr) {
    const int dirs[4][2] = {{1,0},{0,-1},{-1,0},{0,1}};
    int dir = string("v<^>").find(instr), count = 0;
    while (grid.at(i).at(j) == '@' || grid.at(i).at(j) == 'O')
        i += dirs[dir][0], j += dirs[dir][1], count++;
    if (grid.at(i).at(j) == '.')
        return count;
    return 0;
}

int part2(vector<vector<char>> grid, vector<char> instructions) {
    for (int i = 0; i < grid.size(); i++) {
        vector<char> newRow;
        for (char c: grid.at(i)) {
            if (c == '#') newRow.insert(newRow.end(), {'#','#'});
            if (c == 'O') newRow.insert(newRow.end(), {'[',']'});
            if (c == '.') newRow.insert(newRow.end(), {'.','.'});
            if (c == '@') newRow.insert(newRow.end(), {'@', '.'});
        }
        grid.at(i) = newRow;
    }
    int ci = 0, cj = 0;
    for (int i = 0; i < grid.size(); i++)
        for (int j = 0; j < grid.at(i).size(); j++)
            if (grid.at(i).at(j) == '@')
                ci = i, cj = j;
    for (char i: instructions) {
        const int dirs[4][2] = {{1,0},{0,-1},{-1,0},{0,1}};
        int dir = string("v<^>").find(i);
        vector<vector<char>> oldGrid(grid);
        if (makeMove(grid, ci, cj, dir))
            ci += dirs[dir][0], cj += dirs[dir][1];
        else
            grid = oldGrid;
    }
    int result = 0;
    for (int i = 0; i < grid.size(); i++)
        for (int j = 0; j < grid.at(i).size(); j++)
            if (grid.at(i).at(j) == '[')
                result += 100*i+j;
    return result;
}

int part1(vector<vector<char>> grid, vector<char> instructions) {
    int ci = 0, cj = 0;
    for (int i = 0; i < grid.size(); i++)
        for (int j = 0; j < grid.at(i).size(); j++)
            if (grid.at(i).at(j) == '@')
                ci = i, cj = j;
    for (char i: instructions) {
        int count = isValid(grid, ci, cj, i);
        if (count == 0)
            continue;
        const int dirs[4][2] = {{1,0},{0,-1},{-1,0},{0,1}};
        int dir = string("v<^>").find(i);
        for (int k = count; k > 0; k--) {
            grid.at(ci+k*dirs[dir][0]).at(cj+k*dirs[dir][1]) = grid.at(ci+(k-1)*dirs[dir][0]).at(cj+(k-1)*dirs[dir][1]);
        }
        grid.at(ci).at(cj) = '.';
        ci += dirs[dir][0], cj += dirs[dir][1];
    }
    int result = 0;
    for (int i = 0; i < grid.size(); i++)
        for (int j = 0; j < grid.at(i).size(); j++)
            if (grid.at(i).at(j) == 'O')
                result += 100*i+j;
    return result;
}

int main() {
    string line;
    ifstream input("day15.txt");
    vector<vector<char>> grid;
    vector<char> instructions;
    bool endGrid = false;
    while (getline(input, line)) {
        if (line.size() == 0) {
            endGrid = true;
            continue;
        }
        if (endGrid) {
            vector<char> lineChars(line.begin(), line.end());
            instructions.insert(instructions.end(), lineChars.begin(), lineChars.end());
        } else {
            vector<char> lineChars(line.begin(), line.end());
            grid.push_back(lineChars);
        }
    }
    cout << part1(grid, instructions) << endl;
    cout << part2(grid, instructions) << endl;
    return 0;
}