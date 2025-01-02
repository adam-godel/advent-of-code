use std::fs;

fn part2(data: &str) -> String {
    let mut iter = data.lines();
    let mut line = iter.next().unwrap();
    let mut stacks: Vec<Vec<char>> = vec![Vec::new(); 9];
    while !line.is_empty() {
        for (i, ch) in line.chars().skip(1).step_by(4).enumerate() {
            if ch.is_alphabetic() {
                stacks[i].insert(0, ch);
            }
        }
        line = iter.next().unwrap();
    }
    for line in iter {
        let vals: Vec<&str> = line.split_whitespace().collect();
        let mut to_push: Vec<char> = Vec::new();
        for _ in 0..vals[1].parse().unwrap() {
            to_push.insert(
                0,
                stacks[(vals[3].chars().next().unwrap() as usize) - ('0' as usize) - 1]
                    .pop()
                    .unwrap(),
            );
        }
        for i in to_push {
            stacks[(vals[5].chars().next().unwrap() as usize) - ('0' as usize) - 1].push(i);
        }
    }
    let mut result = String::new();
    for i in stacks {
        result.push(i[i.len() - 1]);
    }
    result
}

fn part1(data: &str) -> String {
    let mut iter = data.lines();
    let mut line = iter.next().unwrap();
    let mut stacks: Vec<Vec<char>> = vec![Vec::new(); 9];
    while !line.is_empty() {
        for (i, ch) in line.chars().skip(1).step_by(4).enumerate() {
            if ch.is_alphabetic() {
                stacks[i].insert(0, ch);
            }
        }
        line = iter.next().unwrap();
    }
    for line in iter {
        let vals: Vec<&str> = line.split_whitespace().collect();
        for _ in 0..vals[1].parse().unwrap() {
            let popped =
                stacks[(vals[3].chars().next().unwrap() as usize) - ('0' as usize) - 1].pop();
            stacks[(vals[5].chars().next().unwrap() as usize) - ('0' as usize) - 1]
                .push(popped.unwrap());
        }
    }
    let mut result = String::new();
    for i in stacks {
        result.push(i[i.len() - 1]);
    }
    result
}

fn main() {
    let data = fs::read_to_string("day5.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
