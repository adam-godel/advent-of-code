use std::fs;

fn part2(data: &str) -> u32 {
    let mut vec: Vec<u32> = data
        .split("\n\n")
        .map(|i| i.lines().map(|j| j.parse::<u32>().unwrap()).sum())
        .collect();
    vec.sort();
    vec[vec.len() - 3..].iter().sum()
}

fn part1(data: &str) -> u32 {
    data.split("\n\n")
        .map(|i| i.lines().map(|j| j.parse::<u32>().unwrap()).sum())
        .max()
        .unwrap()
}

fn main() {
    let data = fs::read_to_string("day1.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
