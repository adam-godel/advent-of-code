#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <vector>
#include <cmath>
using namespace std;

int part1(vector<int> left, vector<int> right)
{
    int result = 0;
    for (int i = 0; i < left.size(); i++)
    {
        result += abs(left.at(i) - right.at(i));
    }
    return result;
}

int part2(vector<int> left, vector<int> right)
{
    int i = 0, j = 0, k = 0, curEqual = 0, result = 0;
    while (i < left.size() && j < right.size())
    {
        if (left.at(i) < right.at(j))
        {
            if (curEqual == left.at(i))
            {
                result += k * curEqual;
            }
            else
            {
                k = 0;
            }
            i++;
        }
        else if (left.at(i) > right.at(j))
        {
            if (curEqual == right.at(j))
            {
                result += k * curEqual;
            }
            else
            {
                k = 0;
            }
            j++;
        }
        else
        {
            if (left.at(i) != curEqual)
            {
                k = 0;
            }
            result += (2 * k + 1) * left.at(i);
            curEqual = left.at(i);
            i++;
            j++;
            k++;
        }
    }
    return result;
}

int main()
{
    string line;
    ifstream input("day1.txt");
    vector<int> left, right;
    while (getline(input, line))
    {
        stringstream s(line);
        int leftNum, rightNum;
        s >> leftNum >> rightNum;
        left.push_back(leftNum);
        right.push_back(rightNum);
    }
    input.close();
    sort(left.begin(), left.end());
    sort(right.begin(), right.end());
    cout << part1(left, right) << endl;
    cout << part2(left, right) << endl;
    return 0;
}