#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <array>
#include <regex>
#include <cmath>
using namespace std;

vector<array<int,2>> oneSecond(vector<array<int,2>> pos, vector<array<int,2>> vel, int WIDTH, int HEIGHT) {
    for (int i = 0; i < pos.size(); i++) {
        int newX = pos.at(i).at(0)+vel.at(i).at(0), newY = pos.at(i).at(1)+vel.at(i).at(1);
        newX = newX % WIDTH >= 0 ? newX % WIDTH : newX % WIDTH + WIDTH;
        newY = newY % HEIGHT >= 0 ? newY % HEIGHT : newY % HEIGHT + HEIGHT;
        pos.at(i).at(0) = newX, pos.at(i).at(1) = newY;
    }
    return pos;
}

int part2(vector<vector<int>> data) {
    const int WIDTH = 101, HEIGHT = 103;
    vector<array<int,2>> pos, vel;
    vector<array<double,2>> variance;
    for (vector<int> i: data) {
        pos.push_back({i.at(0), i.at(1)});
        vel.push_back({i.at(2), i.at(3)});
    }
    for (int k = 0; k < 10000; k++) {
        pos = oneSecond(pos, vel, WIDTH, HEIGHT);
        double meanX = 0.0, meanY = 0.0;
        for (array<int,2> i: pos)
            meanX += i.at(0), meanY += i.at(1);
        meanX /= pos.size(), meanY /= pos.size();
        double varX = 0.0, varY = 0.0;
        for (array<int,2> i: pos)
            varX += pow(i.at(0)-meanX,2), varY += pow(i.at(1)-meanY,2);
        varX /= pos.size(), varY /= pos.size();
        variance.push_back({varX,varY});
    }
    int min = INT_MAX, minSec = 0;
    for (int i = 0; i < variance.size(); i++) {
        if (sqrt(pow(variance.at(i).at(0),2)+pow(variance.at(i).at(1),2)) < min) {
            min = sqrt(pow(variance.at(i).at(0),2)+pow(variance.at(i).at(1),2));
            minSec = i+1;
        }
    }
    return minSec;
}

int part1(vector<vector<int>> data) {
    const int WIDTH = 101, HEIGHT = 103;
    vector<array<int,2>> pos, vel;
    for (vector<int> i: data) {
        pos.push_back({i.at(0), i.at(1)});
        vel.push_back({i.at(2), i.at(3)});
    }
    for (int k = 0; k < 100; k++)
        pos = oneSecond(pos, vel, WIDTH, HEIGHT);
    array<int,4> quads = {0};
    for (array<int,2> i: pos) {
        if (i.at(0) < WIDTH/2 && i.at(1) < HEIGHT/2)
            quads.at(0)++;
        else if (i.at(0) > WIDTH/2 && i.at(1) < HEIGHT/2)
            quads.at(1)++;
        else if (i.at(0) < WIDTH/2 && i.at(1) > HEIGHT/2)
            quads.at(2)++;
        else if (i.at(0) > WIDTH/2 && i.at(1) > HEIGHT/2)
            quads.at(3)++;
    }
    int result = 1;
    for (int i: quads)
        result *= i;
    return result;
}

int main() {
    string line;
    ifstream input("day14.txt");
    vector<vector<int>> data;
    vector<int> temp;
    while (getline(input, line)) {
        regex re("(?!-?\\d+(\\.\\d+)*)");
        sregex_token_iterator first{line.begin(), line.end(), re, -1}, last;
        vector<string> result{first, last};
        vector<int> vals;
        for (string s: result)
            if (s.size() > 0)
                vals.push_back(stoi(s));
        data.push_back(vals);
    }
    cout << part1(data) << endl;
    cout << part2(data) << endl;
    return 0;
}