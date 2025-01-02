#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <vector>
#include <iterator>
#include <regex>
using namespace std;

struct not_digit {
    bool operator()(const char c) {
        return c != ' ' && !isdigit(c);
    }
};

vector<int> extractIntegers(string str) { 
    regex regex(R"(\d+)");
    smatch match;
    vector<int> result;
    while (regex_search(str, match, regex)) {
        result.push_back(stoi(match.str()));
        str = match.suffix();
    }
    return result;
}

vector<string> match(vector<string> data, regex r) {
    vector<string> result;
    for (string s: data) {
        auto it = sregex_iterator(s.begin(), s.end(), r);
        auto end = sregex_iterator();
        for (; it != end; ++it) {
            auto match = *it;
            auto element = match[0].str();
            result.push_back(element);
        }
    }
    return result;
}

int part1(vector<string> data) {
    regex r{R"(mul\((\d+),\s*(\d+)\))"};
    vector<string> elements = match(data, r);
    int result = 0;
    for (string s: elements) {
        vector<int> ints = extractIntegers(s);
        result += ints.at(0)*ints.at(1);
    }
    return result;
}

int part2(vector<string> data) {
    regex r{R"(mul\((\d+),\s*(\d+)\)|do\(\)|don't\(\))"};
    vector<string> elements = match(data, r);
    int result = 0;
    bool enabled = true;
    for (string s: elements) {
        if (s == "do()") {
            enabled = true;
        } else if (s == "don't()") {
            enabled = false;
        } else if (enabled) {
            vector<int> ints = extractIntegers(s);
            result += ints.at(0)*ints.at(1);
        }
    }
    return result;
}

int main() {
    string line;
    ifstream input("day3.txt");
    vector<string> data; 
    while (getline(input, line)) {
        data.push_back(line);
    }
    input.close();
    cout << part1(data) << endl;
    cout << part2(data) << endl;
    return 0;
}