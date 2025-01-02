use std::{cmp, fs};

fn recur(val1: &str, val2: &str) -> cmp::Ordering {
    let tmp;
    match (val1.chars().next().unwrap(), val2.chars().next().unwrap()) {
        (a, b) if a == b => recur(&val1[1..], &val2[1..]),
        (']', _) => cmp::Ordering::Less,
        (_, ']') => cmp::Ordering::Greater,
        ('[', b) => {
            tmp = format!("{}]{}", b, &val2[1..]);
            recur(&val1[1..], &tmp)
        }
        (a, '[') => {
            tmp = format!("{}]{}", a, &val1[1..]);
            recur(&tmp, &val2[1..])
        }
        (a, b) => a.cmp(&b),
    }
}

fn part2(data: &str) -> usize {
    let mut iter = data.lines();
    let mut vec = vec!["[[2]]".to_string(), "[[6]]".to_string()];
    loop {
        vec.push(iter.next().unwrap().replace("10", "A"));
        vec.push(iter.next().unwrap().replace("10", "A"));
        if iter.next().is_none() {
            break;
        }
    }
    vec.sort_by(|a, b| recur(a, b));
    (vec.iter().position(|i| i == "[[2]]").unwrap() + 1)
        * (vec.iter().position(|i| i == "[[6]]").unwrap() + 1)
}

fn part1(data: &str) -> u32 {
    let mut iter = data.lines();
    let mut result = 0;
    let mut idx = 1;
    loop {
        let lines = (
            &iter.next().unwrap().replace("10", "A"),
            &iter.next().unwrap().replace("10", "A"),
        );
        if recur(lines.0, lines.1) == cmp::Ordering::Less {
            result += idx;
        }
        idx += 1;
        if iter.next().is_none() {
            break;
        }
    }
    result
}

fn main() {
    let data = fs::read_to_string("day13.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
