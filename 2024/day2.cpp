#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <vector>
using namespace std;

vector<int> split (const string &s, char delim) {
    vector<int> result;
    stringstream ss(s);
    string item;
    while (getline(ss, item, delim)) {
        result.push_back(stoi(item));
    }
    return result;
}

void get_subset_rec(vector<vector<int>>& subsets, int n, int k, int index, vector<int>& A, vector<int>& current_subset) {
    if (n < k) return;   
    if (k == 0) {
        subsets.push_back(current_subset);
        return;
    }  
    get_subset_rec(subsets, n-1, k, index+1, A, current_subset);
    current_subset.push_back(A[index]);
    get_subset_rec(subsets, n-1, k-1, index+1, A, current_subset);
    current_subset.pop_back();
    return;
}

void get_subset(vector<vector<int>>& subsets, int subset_length, vector<int>& A) {
    vector<int> current_subset;
    get_subset_rec(subsets, A.size(), subset_length, 0, A, current_subset);
}

bool compare(vector<int> line) {
    int sgn = ((line.at(1)-line.at(0)) > 0) - ((line.at(1)-line.at(0)) < 0);
    if (sgn == 0) return false;
    for (int i = 1; i < line.size(); i++) {
        int diff = line.at(i)-line.at(i-1);
        if ((diff > 0) - (diff < 0) != sgn || abs(diff) < 1 || abs(diff) > 3) {
            return false;
        }
    }
    return true;
}

int part1(vector<vector<int>> data) {
    int result = 0;
    for (vector<int> line: data) {
        if (compare(line)) {
            result++;
        }
    }
    return result;
}

int part2(vector<vector<int>> data) {
    int result = 0;
    for (vector<int> line: data) {
        bool safe = false;
        vector<vector<int>> subsets;
        get_subset(subsets, line.size()-1, line);
        for (vector<int> j : subsets) {
            if (compare(j)) {
                safe = true;
                break;
            }
        }
        if (safe) result++;
    }
    return result;
}

int main() {
    string line;
    ifstream input("day2.txt");
    vector<vector<int>> data; 
    while (getline(input, line)) {
        data.push_back(split(line, ' '));
    }
    input.close();
    cout << part1(data) << endl;
    cout << part2(data) << endl;
    return 0;
}