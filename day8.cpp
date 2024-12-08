#include <iostream>
#include <fstream>
#include <string>
#include <vector>
using namespace std;

int part2(vector<vector<char>> grid) {
    int i = 0, j = -1;
    vector<vector<bool>> antinodes(grid.size(), vector<bool>(grid.at(0).size(), false));
     while (i < grid.size()) {
        do {
            if (j == -1 || j < grid.at(i).size()) {
                j++;
            }
            if (j == grid.at(i).size()) {
                i++;
                j = 0;
            }
        } while (i < grid.size() && grid.at(i).at(j) == '.');
        int mi = i, mj = j+1;
        while (mi < grid.size()) {
            do {
                if (mj < grid.at(mi).size()) {
                    mj++;
                }
                if (mj == grid.at(mi).size()) {
                    mi++;
                    mj = 0;
                }
            } while (mi < grid.size() && grid.at(mi).at(mj) != grid.at(i).at(j));
            if (mi == grid.size()) {
                break;
            }
            int ci = i, cj = j;
            while (ci >= 0 && ci < antinodes.size() && cj >= 0 && cj < antinodes.at(ci).size()) {
                antinodes.at(ci).at(cj) = true;
                ci += mi-i, cj += mj-j;
            }
            ci = i, cj = j;
            while (ci >= 0 && ci < antinodes.size() && cj >= 0 && cj < antinodes.at(ci).size()) {
                antinodes.at(ci).at(cj) = true;
                ci -= mi-i, cj -= mj-j;
            }
        }
    }
    int result = 0;
    for (vector<bool> i: antinodes) {
        for (bool j: i) {
            if (j) {
                result++;
            }
        }
    }
    return result;
}

int part1(vector<vector<char>> grid) {
    int i = 0, j = -1;
    vector<vector<bool>> antinodes(grid.size(), vector<bool>(grid.at(0).size(), false));
    while (i < grid.size()) {
        do {
            if (j == -1 || j < grid.at(i).size()) {
                j++;
            }
            if (j == grid.at(i).size()) {
                i++;
                j = 0;
            }
        } while (i < grid.size() && grid.at(i).at(j) == '.');
        int mi = i, mj = j+1;
        while (mi < grid.size()) {
            do {
                if (mj < grid.at(mi).size()) {
                    mj++;
                }
                if (mj == grid.at(mi).size()) {
                    mi++;
                    mj = 0;
                }
            } while (mi < grid.size() && grid.at(mi).at(mj) != grid.at(i).at(j));
            if (mi == grid.size()) {
                break;
            }
            int di = mi-i, dj = mj-j;
            if (i-di >= 0 && i-di < antinodes.size() && j-dj >= 0 && j-dj < antinodes.at(mi).size()) {
                antinodes.at(i-di).at(j-dj) = true;
            }
            if (mi+di >= 0 && mi+di < antinodes.size() && mj+dj >= 0 && mj+dj < antinodes.at(mi).size()) {
                antinodes.at(mi+di).at(mj+dj) = true;
            }
        }
    }
    int result = 0;
    for (vector<bool> i: antinodes) {
        for (bool j: i) {
            if (j) {
                result++;
            }
        }
    }
    return result;
}

int main() {
    string line;
    ifstream input("day8.txt");
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