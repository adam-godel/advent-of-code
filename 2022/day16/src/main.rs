use std::{
    cmp,
    collections::{BinaryHeap, HashMap},
    fs,
};

struct Orig<'a> {
    flow_rate: u32,
    neighbors: Vec<&'a str>,
}

struct Valve<'a> {
    flow_rate: u32,
    dists: HashMap<&'a str, u32>,
}

#[derive(PartialEq, Eq)]
struct Pos<'a> {
    valve: &'a str,
    step: u32,
    res: u32,
    visited: Vec<&'a str>,
}

impl Ord for Pos<'_> {
    fn cmp(&self, other: &Self) -> cmp::Ordering {
        self.res.cmp(&other.res)
    }
}

impl PartialOrd for Pos<'_> {
    fn partial_cmp(&self, other: &Self) -> Option<cmp::Ordering> {
        Some(self.cmp(other))
    }
}

fn part2(data: &str) -> u32 {
    let mut orig: HashMap<&str, Orig> = HashMap::new();
    for line in data.lines() {
        let list: Vec<&str> = line.split_whitespace().collect();
        orig.insert(
            list[1],
            Orig {
                flow_rate: list[4][5..list[4].len() - 1].parse().unwrap(),
                neighbors: line
                    .split("valves ")
                    .nth(1)
                    .unwrap_or_else(|| line.split("valve ").nth(1).unwrap())
                    .split(", ")
                    .collect(),
            },
        );
    }
    let mut graph: HashMap<&str, Valve> = HashMap::new();
    for i in orig.keys() {
        if orig[i].flow_rate == 0 && *i != "AA" {
            continue;
        }
        let mut visited: Vec<&str> = Vec::new();
        let mut dists: HashMap<&str, u32> = HashMap::new();
        let mut queue: Vec<(&str, u32)> = Vec::new();
        queue.insert(0, (i, 0));
        while !queue.is_empty() {
            let cur = queue.pop().unwrap();
            if visited.contains(&cur.0) {
                continue;
            }
            if cur.1 > 0 && (orig[cur.0].flow_rate > 0 || cur.0 == "AA") {
                dists.insert(cur.0, cur.1);
            }
            visited.push(cur.0);
            for j in &orig[cur.0].neighbors {
                queue.insert(0, (j, cur.1 + 1));
            }
        }
        graph.insert(
            i,
            Valve {
                flow_rate: orig[i].flow_rate,
                dists,
            },
        );
    }
    let mut max = 0;
    let mut pq = BinaryHeap::new();
    pq.push(Pos {
        valve: "AA",
        step: 0,
        res: 0,
        visited: Vec::new(),
    });
    while !pq.is_empty() {
        let cur = pq.pop().unwrap();
        if cur.step > 26 {
            continue;
        }
        let mut new_max = 0;
        let mut pq2 = BinaryHeap::new();
        pq2.push(Pos {
            valve: "AA",
            step: 0,
            res: 0,
            visited: cur.visited.clone(),
        });
        while !pq2.is_empty() {
            let cur = pq2.pop().unwrap();
            if cur.step > 26 {
                continue;
            }
            if cur.res + graph[cur.valve].flow_rate * (26 - cur.step) > new_max {
                new_max = cur.res + graph[cur.valve].flow_rate * (26 - cur.step);
            }
            for (key, value) in (&graph[cur.valve].dists).into_iter() {
                if !cur.visited.contains(key) {
                    let mut new_visited = cur.visited.clone();
                    new_visited.push(cur.valve);
                    pq2.push(Pos {
                        valve: key,
                        step: cur.step + 1 + value,
                        res: cur.res + graph[cur.valve].flow_rate * (26 - cur.step),
                        visited: new_visited,
                    });
                }
            }
        }
        if cur.res + new_max > max {
            max = cur.res + new_max;
        }
        if cur.res + graph[cur.valve].flow_rate * (26 - cur.step) > max {
            max = cur.res + graph[cur.valve].flow_rate * (26 - cur.step);
        }
        for (key, value) in (&graph[cur.valve].dists).into_iter() {
            if !cur.visited.contains(key) {
                let mut new_visited = cur.visited.clone();
                new_visited.push(cur.valve);
                pq.push(Pos {
                    valve: key,
                    step: cur.step + 1 + value,
                    res: cur.res + graph[cur.valve].flow_rate * (26 - cur.step),
                    visited: new_visited,
                });
            }
        }
    }
    max
}

fn part1(data: &str) -> u32 {
    let mut orig: HashMap<&str, Orig> = HashMap::new();
    for line in data.lines() {
        let list: Vec<&str> = line.split_whitespace().collect();
        orig.insert(
            list[1],
            Orig {
                flow_rate: list[4][5..list[4].len() - 1].parse().unwrap(),
                neighbors: line
                    .split("valves ")
                    .nth(1)
                    .unwrap_or_else(|| line.split("valve ").nth(1).unwrap())
                    .split(", ")
                    .collect(),
            },
        );
    }
    let mut graph: HashMap<&str, Valve> = HashMap::new();
    for i in orig.keys() {
        if orig[i].flow_rate == 0 && *i != "AA" {
            continue;
        }
        let mut visited: Vec<&str> = Vec::new();
        let mut dists: HashMap<&str, u32> = HashMap::new();
        let mut queue: Vec<(&str, u32)> = Vec::new();
        queue.insert(0, (i, 0));
        while !queue.is_empty() {
            let cur = queue.pop().unwrap();
            if visited.contains(&cur.0) {
                continue;
            }
            if cur.1 > 0 && (orig[cur.0].flow_rate > 0 || cur.0 == "AA") {
                dists.insert(cur.0, cur.1);
            }
            visited.push(cur.0);
            for j in &orig[cur.0].neighbors {
                queue.insert(0, (j, cur.1 + 1));
            }
        }
        graph.insert(
            i,
            Valve {
                flow_rate: orig[i].flow_rate,
                dists,
            },
        );
    }
    let mut max = 0;
    let mut pq = BinaryHeap::new();
    pq.push(Pos {
        valve: "AA",
        step: 0,
        res: 0,
        visited: Vec::new(),
    });
    while !pq.is_empty() {
        let cur = pq.pop().unwrap();
        if cur.step > 30 {
            continue;
        }
        if cur.res + graph[cur.valve].flow_rate * (30 - cur.step) > max {
            max = cur.res + graph[cur.valve].flow_rate * (30 - cur.step);
        }
        for (key, value) in (&graph[cur.valve].dists).into_iter() {
            if !cur.visited.contains(key) {
                let mut new_visited = cur.visited.clone();
                new_visited.push(cur.valve);
                pq.push(Pos {
                    valve: key,
                    step: cur.step + 1 + value,
                    res: cur.res + graph[cur.valve].flow_rate * (30 - cur.step),
                    visited: new_visited,
                });
            }
        }
    }
    max
}

fn main() {
    let data = fs::read_to_string("day16.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
