use std::{collections::HashSet, fs};

fn part2(data: &str) -> u32 {
    let vec: Vec<&str> = data.lines().collect();
    let mut result = 0;
    for i in 0..vec.len() / 3 {
        let vec: Vec<HashSet<char>> = vec![
            HashSet::from_iter(vec[3 * i].chars()),
            HashSet::from_iter(vec[3 * i + 1].chars()),
            HashSet::from_iter(vec[3 * i + 2].chars()),
        ];
        let inter = vec[1..].iter().fold(vec[0].clone(), |mut acc, set| {
            acc.retain(|item| set.contains(item));
            acc
        });
        let ch: char = *inter.iter().next().unwrap();
        let res = if ch.is_ascii_uppercase() {
            (ch as u32) - 38
        } else {
            (ch as u32) - 96
        };
        result += res;
    }
    result
}

fn part1(data: &str) -> u32 {
    data.lines()
        .map(|i| {
            let a: HashSet<char> = HashSet::from_iter(i[..i.len() / 2].chars());
            let b: HashSet<char> = HashSet::from_iter(i[i.len() / 2..].chars());
            let ch: char = *a.intersection(&b).next().unwrap();
            if ch.is_ascii_uppercase() {
                (ch as u32) - 38
            } else {
                (ch as u32) - 96
            }
        })
        .sum()
}

fn main() {
    let data = fs::read_to_string("day3.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
