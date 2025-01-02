#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <queue>
#include <map>
using namespace std;

long part1(map<string, bool> wires, queue<string> data) {
    while (!data.empty()) {
        string cur = data.front();
        if (wires.count(cur.substr(0,3)) && (wires.count(cur.substr(7,3)) || wires.count(cur.substr(8,3)))) {
            if (cur.substr(4,3) == "AND") {
                if (!wires.count(cur.substr(15,3)))
                    wires.insert({cur.substr(15,3), false});
                wires.at(cur.substr(15,3)) = wires.at(cur.substr(0,3)) & wires.at(cur.substr(8,3));
            } else if (cur.substr(4,2) == "OR") {
                if (!wires.count(cur.substr(14,3)))
                    wires.insert({cur.substr(14,3), false});
                wires.at(cur.substr(14,3)) = wires.at(cur.substr(0,3)) | wires.at(cur.substr(7,3));
            } else if (cur.substr(4,3) == "XOR") {
                if (!wires.count(cur.substr(15,3)))
                    wires.insert({cur.substr(15,3), false});
                wires.at(cur.substr(15,3)) = wires.at(cur.substr(0,3)) ^ wires.at(cur.substr(8,3));
            }
        } else {
            data.push(data.front());
        }
        data.pop();
    }
    string val;
    for (int i = 0; wires.count(i < 10 ? "z0"+to_string(i) : "z"+to_string(i)); i++)
        val = to_string(wires.at(i < 10 ? "z0"+to_string(i) : "z"+to_string(i)))+val;
    bitset<46> result{val};
    return result.to_ullong();
}

void part2helper(map<string, bool> wires, queue<string> data) {
    string valX, valY;
    for (int i = 0; wires.count(i < 10 ? "x0"+to_string(i) : "x"+to_string(i)); i++)
        valX = to_string(wires.at(i < 10 ? "x0"+to_string(i) : "x"+to_string(i)))+valX;
    for (int i = 0; wires.count(i < 10 ? "y0"+to_string(i) : "y"+to_string(i)); i++)
        valY = to_string(wires.at(i < 10 ? "y0"+to_string(i) : "y"+to_string(i)))+valY;
    bitset<45> x{valX}, y{valY};
    bitset<46> sum{x.to_ullong()+y.to_ullong()};
    vector<string> result = {"z05", "jst", "z30", "gwc", "z15", "dnt"};
    while (!data.empty()) {
        string cur = data.front();
        if (wires.count(cur.substr(0,3)) && (wires.count(cur.substr(7,3)) || wires.count(cur.substr(8,3)))) {
            for (int i = 0; i < result.size()-1; i += 2) {
                if (cur.substr(14,3) == result.at(i))
                    cur.replace(14, 3, result.at(i+1));
                else if (cur.substr(15,3) == result.at(i))
                    cur.replace(15, 3, result.at(i+1));
                else if (cur.substr(14,3) == result.at(i+1))
                    cur.replace(14, 3, result.at(i));
                else if (cur.substr(15,3) == result.at(i+1))
                    cur.replace(15, 3, result.at(i));
                if (cur != data.front())
                    cout << cur << endl;
            }
            if (cur.substr(4,3) == "AND") {
                if (!wires.count(cur.substr(15,3)))
                    wires.insert({cur.substr(15,3), false});
                wires.at(cur.substr(15,3)) = wires.at(cur.substr(0,3)) & wires.at(cur.substr(8,3));
            } else if (cur.substr(4,2) == "OR") {
                if (!wires.count(cur.substr(14,3)))
                    wires.insert({cur.substr(14,3), false});
                wires.at(cur.substr(14,3)) = wires.at(cur.substr(0,3)) | wires.at(cur.substr(7,3));
            } else if (cur.substr(4,3) == "XOR") {
                if (!wires.count(cur.substr(15,3)))
                    wires.insert({cur.substr(15,3), false});
                wires.at(cur.substr(15,3)) = wires.at(cur.substr(0,3)) ^ wires.at(cur.substr(8,3));
            }
        } else {
            data.push(cur);
        }
        data.pop();
    }
    string val;
    for (int i = 0; wires.count(i < 10 ? "z0"+to_string(i) : "z"+to_string(i)); i++)
        val = to_string(wires.at(i < 10 ? "z0"+to_string(i) : "z"+to_string(i)))+val;
    bitset<46> temp{val};
    cout << (temp ^ sum) << endl; // final two are mcm and gdf
}

int main() {
    string line;
    ifstream input("day24.txt");
    map<string, bool> wires;
    queue<string> data;
    bool endInit = false;
    while (getline(input, line)) {
        if (line.size() == 0) {
            endInit = true;
            continue;
        }
        if (endInit)
            data.push(line);
        else
            wires.insert({line.substr(0,3), line.at(5) == '1'});
    }
    input.close();
    cout << part1(wires, data) << endl;
    part2helper(wires, data); // I solved part 2 using manual inspection along with this helper function 
    return 0;
}