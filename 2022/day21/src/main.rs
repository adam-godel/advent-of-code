use num::complex::Complex;
use std::{collections::HashMap, fs};

fn recur(map: &HashMap<&str, &str>, op: &str) -> Complex<f64> {
    if op == "humn" {
        Complex::new(0.0, 1.0)
    } else if op == "root" {
        let res = (recur(map, &map[op][..4]), recur(map, &map[op][7..]));
        Complex::new((res.0.re - res.1.re) / (res.1.im - res.0.im), 0.0)
    } else {
        map[op]
            .parse::<Complex<f64>>()
            .unwrap_or_else(|_| match map[op].chars().nth(5).unwrap() {
                '+' => recur(map, &map[op][..4]) + recur(map, &map[op][7..]),
                '-' => recur(map, &map[op][..4]) - recur(map, &map[op][7..]),
                '*' => recur(map, &map[op][..4]) * recur(map, &map[op][7..]),
                '/' => recur(map, &map[op][..4]) / recur(map, &map[op][7..]),
                _ => Complex::new(0.0, 0.0),
            })
    }
}

fn part2(map: &HashMap<&str, &str>) -> i64 {
    recur(map, "root").re as i64
}

fn part1(map: &HashMap<&str, &str>, op: &str) -> i64 {
    map[op]
        .parse()
        .unwrap_or_else(|_| match map[op].chars().nth(5).unwrap() {
            '+' => part1(map, &map[op][..4]) + part1(map, &map[op][7..]),
            '-' => part1(map, &map[op][..4]) - part1(map, &map[op][7..]),
            '*' => part1(map, &map[op][..4]) * part1(map, &map[op][7..]),
            '/' => part1(map, &map[op][..4]) / part1(map, &map[op][7..]),
            _ => 0,
        })
}

fn main() {
    let data = fs::read_to_string("day21.txt").unwrap();
    let map = data
        .lines()
        .map(|i| (&i[..4], &i[6..]))
        .collect::<HashMap<&str, &str>>();
    println!("{}\n{}", part1(&map, "root"), part2(&map));
}
