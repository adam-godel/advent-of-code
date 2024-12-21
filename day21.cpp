#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <map>
using namespace std;

struct params {
    char start, end;
    int layer;
    bool operator==(const params& o) const {
        return start == o.start && end == o.end && layer == o.layer;
    }
    bool operator<(const params& o) const {
        return (layer < o.layer || (layer == o.layer && end < o.end) || (layer == o.layer && end == o.end && start < o.start));
    }
};

const vector<vector<char>> numPad = {
    {'7', '8', '9'},
    {'4', '5', '6'},
    {'1', '2', '3'},
    {'\0', '0', 'A'}
};

const vector<vector<char>> dirPad = {
    {'\0', '^', 'A'},
    {'<', 'v', '>'}
};

vector<string> bfs(pair<int,int> start, pair<int,int> end, int layer) {
    if (!(layer == 0 && start.first >= 0 && start.first <= 3 && start.second >= 0 && start.second <= 2 && numPad.at(start.first).at(start.second) != '\0') && !(layer > 0 && start.first >= 0 && start.first <= 1 && start.second >= 0 && start.second <= 2 && dirPad.at(start.first).at(start.second) != '\0'))
        return {};
    if (start == end)
        return {"A"};
    vector<string> result;
    if (end.first < start.first)
        for (string s: bfs(make_pair(start.first-1, start.second), end, layer))
            result.push_back(s+'^');
    else if (end.first > start.first)
        for (string s: bfs(make_pair(start.first+1, start.second), end, layer))
            result.push_back(s+'v');
    if (end.second < start.second)
        for (string s: bfs(make_pair(start.first, start.second-1), end, layer))
            result.push_back(s+'<');
    else if (end.second > start.second)
        for (string s: bfs(make_pair(start.first, start.second+1), end, layer))
            result.push_back(s+'>');
    return result;
}

long shortestPath(char start, char end, int layer, int numLayers, map<params, long> &cache) {
    if (layer == numLayers)
        return 1;
    if (cache.find({start, end, layer}) != cache.end())
        return cache.at({start, end, layer});
    long min = LONG_MAX;
    pair<int,int> sidx, eidx;
    vector<vector<char>> toDraw = layer == 0 ? numPad : dirPad;
    for (int i = 0; i < toDraw.size(); i++)
        for (int j = 0; j < toDraw.at(i).size(); j++) {
            if (toDraw.at(i).at(j) == start)
                sidx = make_pair(i, j);
            if (toDraw.at(i).at(j) == end)
                eidx = make_pair(i, j);
        }
    for (string path: bfs(sidx, eidx, layer)) {
        reverse(path.begin(), path.end());
        path.insert(path.begin(), 'A');
        long pathLength = 0;
        for (int i = 0; i < path.size()-1; i++)
            pathLength += shortestPath(path.at(i), path.at(i+1), layer+1, numLayers, cache);
        if (pathLength < min)
            min = pathLength;
    }
    cache.insert({{start, end, layer}, min});
    return min;
}

long part2(vector<string> data) {
    long result = 0;
    map<params, long> cache;
    for (string s: data) {
        s.insert(s.begin(), 'A');
        long fullLength = 0;
        for (int i = 0; i < s.size()-1; i++)
            fullLength += shortestPath(s.at(i), s.at(i+1), 0, 26, cache);
        result += fullLength*stoi(s.substr(1,3));
    }
    return result;
}

int part1(vector<string> data) {
    int result = 0;
    map<params, long> cache;
    for (string s: data) {
        s.insert(s.begin(), 'A');
        int fullLength = 0;
        for (int i = 0; i < s.size()-1; i++)
            fullLength += shortestPath(s.at(i), s.at(i+1), 0, 3, cache);
        result += fullLength*stoi(s.substr(1,3));
    }
    return result;
}

int main() {
    string line;
    ifstream input("day21.txt");
    vector<string> data;
    while (getline(input, line))
        data.push_back(line);
    input.close();
    cout << part1(data) << endl;
    cout << part2(data) << endl;
    return 0;
}