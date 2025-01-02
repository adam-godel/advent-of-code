#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <cmath>
using namespace std;

vector<int> split(const string &s, char delim) {
    vector<int> result;
    stringstream ss(s);
    string item;
    while (getline(ss, item, delim)) {
        if (any_of(item.begin(), item.end(), ::isdigit))
            result.push_back(stoi(item.substr(item.find_first_of("0123456789"), item.find_first_not_of("0123456789", item.find_first_of("0123456789")))));
    }
    return result;
}

int step(long A) {
    long B = A % 8;
    B ^= 1;
    long C = A/pow(2,B);
    B ^= 5;
    B ^= C;
    return B % 8;
}

bool find(vector<int> instructions, long toTest, int idx, long &result) {
    if (idx == -1) {
        result = toTest;
        return true;
    }
    for (int j = 0; j < 8; j++)
        if (step(toTest*8+j) == instructions.at(idx) && find(instructions, toTest*8+j, idx-1, result))
            return true;
    return false;
}

long part2(vector<int> instructions) {
    long result = 0;
    find(instructions, 0, instructions.size()-1, result);
    return result;
}

string part1(vector<int> instructions, int registers[3]) {
    vector<int> output;
    for (int ptr = 0; ptr < instructions.size(); ptr += 2) {
        if (instructions.at(ptr) == 0) {
            registers[0] /= pow(2, instructions.at(ptr+1) < 4 ? instructions.at(ptr+1) : registers[instructions.at(ptr+1)-4]);
        } else if (instructions.at(ptr) == 1) {
            registers[1] ^= instructions.at(ptr+1);
        } else if (instructions.at(ptr) == 2) {
            registers[1] = (instructions.at(ptr+1) < 4 ? instructions.at(ptr+1) : registers[instructions.at(ptr+1)-4]) % 8;
        } else if (instructions.at(ptr) == 3) {
            if (registers[0] != 0)
                ptr = instructions.at(ptr+1)-2;
        } else if (instructions.at(ptr) == 4) {
            registers[1] ^= registers[2];
        } else if (instructions.at(ptr) == 5) {
            output.push_back((instructions.at(ptr+1) < 4 ? instructions.at(ptr+1) : registers[instructions.at(ptr+1)-4]) % 8);
        } else if (instructions.at(ptr) == 6) {
            registers[1] = registers[0]/pow(2, instructions.at(ptr+1) < 4 ? instructions.at(ptr+1) : registers[instructions.at(ptr+1)-4]);
        } else if (instructions.at(ptr) == 7) {
            registers[2] = registers[0]/pow(2, instructions.at(ptr+1) < 4 ? instructions.at(ptr+1) : registers[instructions.at(ptr+1)-4]);
        }
    }
    string result;
    for (int i = 0; i < output.size()-1; i++)
        result += to_string(output.at(i))+",";
    result += to_string(output.at(output.size()-1));
    return result;
}

int main() {
    string line;
    ifstream input("day17.txt");
    vector<int> instructions;
    int registers[3] = {0,0,0}, idx = 0;
    bool endReg = false;
    while (getline(input, line)) {
        if (line.size() == 0) {
            endReg = true;
            continue;
        }
        if (endReg) {
            instructions = split(line, ',');
        } else {
            line.erase(0, line.find(": ", 0)+2);
            registers[idx++] = stoi(line);
        }
    }
    input.close();
    cout << part1(instructions, registers) << endl;
    cout << part2(instructions) << endl;
    return 0;
}