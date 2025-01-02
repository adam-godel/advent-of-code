use std::fs;

fn part2(data: &str) -> u32 {
    let vec: Vec<_> = data.lines().collect();
    let mut sizes = Vec::new();
    let mut i = 0;
    let mut result = Vec::new();
    while i < vec.len() {
        if vec[i] == "$ ls" {
            let mut temp = 0;
            while i + 1 < vec.len() && vec[i + 1].chars().next().unwrap() != '$' {
                i += 1;
                if !vec[i].starts_with("dir") {
                    temp += vec[i]
                        .split_whitespace()
                        .next()
                        .unwrap()
                        .parse::<u32>()
                        .unwrap();
                }
            }
            sizes.push(temp);
        } else if vec[i] == "$ cd .." {
            let popped = sizes.pop().unwrap();
            result.push(popped);
            *sizes.last_mut().unwrap() += popped;
        }
        i += 1;
    }
    while !sizes.is_empty() {
        let popped = sizes.pop().unwrap();
        result.push(popped);
        if !sizes.is_empty() {
            *sizes.last_mut().unwrap() += popped;
        }
    }
    result.sort();
    let mut val = 0;
    for i in &result[..] {
        if (result[result.len() - 1] - i) < 40000000 {
            val = *i;
            break;
        }
    }
    val
}

fn part1(data: &str) -> u32 {
    let vec: Vec<_> = data.lines().collect();
    let mut sizes = Vec::new();
    let mut i = 0;
    let mut result = 0;
    while i < vec.len() {
        if vec[i] == "$ ls" {
            let mut temp = 0;
            while i + 1 < vec.len() && vec[i + 1].chars().next().unwrap() != '$' {
                i += 1;
                if !vec[i].starts_with("dir") {
                    temp += vec[i]
                        .split_whitespace()
                        .next()
                        .unwrap()
                        .parse::<u32>()
                        .unwrap();
                }
            }
            sizes.push(temp);
        } else if vec[i] == "$ cd .." {
            let popped = sizes.pop().unwrap();
            if popped <= 100000 {
                result += popped;
            }
            *sizes.last_mut().unwrap() += popped;
        }
        i += 1;
    }
    while !sizes.is_empty() {
        let popped = sizes.pop().unwrap();
        if popped <= 100000 {
            result += popped;
        }
        if !sizes.is_empty() {
            *sizes.last_mut().unwrap() += popped;
        }
    }
    result
}

fn main() {
    let data = fs::read_to_string("day7.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
