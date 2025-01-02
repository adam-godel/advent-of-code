use std::fs;

fn part2(data: &str) -> usize {
    let vec: Vec<char> = data.chars().collect();
    let mut i = 14;
    while i < vec.len() {
        let substr = &vec[i - 14..i].to_vec();
        let mut uniq = substr.clone();
        uniq.sort();
        uniq.dedup();
        if substr.len() == uniq.len() {
            break;
        }
        i += 1;
    }
    i
}

fn part1(data: &str) -> usize {
    let vec: Vec<char> = data.chars().collect();
    let mut i = 4;
    while i < vec.len() {
        let substr = &vec[i - 4..i].to_vec();
        let mut uniq = substr.clone();
        uniq.sort();
        uniq.dedup();
        if substr.len() == uniq.len() {
            break;
        }
        i += 1;
    }
    i
}

fn main() {
    let data = fs::read_to_string("day6.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
